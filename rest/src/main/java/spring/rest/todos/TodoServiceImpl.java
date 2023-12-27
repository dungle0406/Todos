package spring.rest.todos;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class TodoServiceImpl implements TodoService {
    private final RedisTemplate<Object, Object> redisTemplate;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final int CACHE_THRESHOLD = 2;

    @Autowired
    public TodoServiceImpl(RedisTemplate<Object, Object> redisTemplate, TodoRepository todoRepository, TodoMapper todoMapper) {
        this.redisTemplate = redisTemplate;
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    @Override
    public void create(TodoDTO dto) {
        todoRepository.save(todoMapper.mapTodoFromDto(dto));
    }

    @Override
    @Cacheable(value = "todos", key = "#dto.hashCode()")
    public List<TodoDTO> findAll(TodoDTO dto) throws JsonProcessingException {
        return fetchAndCache(dto);
    }

    @CachePut(value = "todos", key = "#dto.hashCode()")
    public List<TodoDTO> fetchAndCache(TodoDTO dto) throws JsonProcessingException {
        return todoRepository.findAll(Example.of(todoMapper.mapTodoFromDto(dto)))
                .stream()
                .map(todoMapper::mapDtoFromTodo)
                .collect(Collectors.toList());
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
}
