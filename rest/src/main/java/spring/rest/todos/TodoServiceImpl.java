package spring.rest.todos;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import spring.rest.caches.CacheData;
import spring.rest.caches.CacheDataRepository;
import spring.rest.caches.CacheServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoServiceImpl implements TodoService {
    private final CacheDataRepository cacheDataRepository;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final CacheServiceImpl<TodoDTO> cacheService;
    private final int CACHE_THRESHOLD = 2;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, TodoMapper todoMapper, CacheDataRepository cacheDataRepository, CacheServiceImpl<TodoDTO> cacheService) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.cacheDataRepository = cacheDataRepository;
        this.cacheService = cacheService;
    }

    @Override
    public void create(TodoDTO dto) {
        todoRepository.save(todoMapper.mapTodoFromDto(dto));
    }

    @Override
    @Cacheable(value = "todosCache", key = "'allTodos'")
    public List<TodoDTO> findAll(TodoDTO dto) {
        var optionalCacheData = cacheDataRepository.findById("allTodos");

        if (optionalCacheData.isPresent()) {
            return cacheService.readCachedData(optionalCacheData.get().getValue());
        }

        return todoRepository.findAll(Example.of(todoMapper.mapTodoFromDto(dto)))
                .stream()
                .map(todoMapper::mapDtoFromTodo)
                .peek(todoDTO -> {
                    if (todoDTO.getId() <= CACHE_THRESHOLD) {
                        cacheDataRepository.save(createCacheData(todoDTO));
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(value = "todosCache", key = "'allTodos'")
    public void edit(Long id, TodoDTO dto) throws TodoNotFoundException {
        var optionalCacheData = cacheDataRepository.findById("todo_" + id);

        if (optionalCacheData.isPresent()) {
            cacheService.editCachedData(optionalCacheData.get(), dto);
            return;
        }

        todoRepository.save(todoMapper
                .updateTodoFromDto(dto, todoRepository
                        .findById(id)
                        .orElseThrow(() -> new TodoNotFoundException(id))));
    }

    @Override
    @CacheEvict(value = "todosCache", key = "'allTodos'")
    public void delete(Long id) throws TodoNotFoundException {
        String targetId = "todo_" + id;

        if (cacheDataRepository.findById(targetId).isPresent()) {
            cacheDataRepository.deleteById(targetId);
            return;
        }

        todoRepository.delete(todoRepository
                .findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id)));
    }

    private CacheData createCacheData(TodoDTO todoDto) {
        try {
            return cacheService.createNewCacheData("allTodos", todoDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
