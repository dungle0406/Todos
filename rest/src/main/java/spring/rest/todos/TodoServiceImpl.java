package spring.rest.todos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import spring.rest.caches.CacheData;
import spring.rest.caches.CacheDataRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final CacheDataRepository cacheDataRepository;
    private final ObjectMapper objectMapper;


    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, TodoMapper todoMapper, CacheDataRepository cacheDataRepository, ObjectMapper objectMapper) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.cacheDataRepository = cacheDataRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void create(TodoDTO dto) {
        todoRepository.save(todoMapper.mapTodoFromDto(dto));
    }

    @Override
    @Cacheable(value = "todosCache", key = "'allTodos'")
    public List<TodoDTO> findAll(TodoDTO dto) throws JsonProcessingException {
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById("allTodos");
        if (optionalCacheData.isPresent()) {
            return readCachedData(optionalCacheData.get().getValue());
        }

        return todoRepository.findAll(Example.of(todoMapper.mapTodoFromDto(dto)))
                .stream()
                .map(todoMapper::mapDtoFromTodo)
                .peek(this::cacheTodoDto).collect(Collectors.toList());
    }

    @Override
    public void edit(Long id, TodoDTO dto) throws TodoNotFoundException {
        todoRepository.save(todoMapper
                .updateTodoFromDto(dto, todoRepository
                        .findById(id)
                        .orElseThrow(() -> new TodoNotFoundException(id))));
    }

    @Override
    public void delete(Long id) throws TodoNotFoundException {
        todoRepository.delete(todoRepository
                .findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id)));
    }

    private void cacheTodoDto(TodoDTO dto) {
        if (dto.getId() <= 2) {
            try {
                cacheDataRepository.save(new CacheData("allTodos", objectMapper.writeValueAsString(dto)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<TodoDTO> readCachedData(String optionalCacheDataAsString) {
        try {
            return objectMapper.readValue(optionalCacheDataAsString, new TypeReference<List<TodoDTO>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
