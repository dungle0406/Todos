package spring.rest.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final int CACHE_THRESHOLD = 2;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, TodoMapper todoMapper) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    @Override
    public void create(TodoDTO dto) {
        todoRepository.save(todoMapper.mapTodoFromDto(dto));
    }

    @Override
    @Cacheable(value = "todosCache", keyGenerator = "keyGenerator")
    public List<TodoDTO> findAll(TodoDTO dto) {
        return todoRepository.findAll(Example.of(todoMapper.mapTodoFromDto(dto)))
                .stream()
                .map(todoMapper::mapDtoFromTodo)
                .collect(Collectors.toList());
    }

    @Override
//    @CachePut(value = "todosCache", key = "'allTodos'")
    public void edit(Long id, TodoDTO dto) throws TodoNotFoundException {
        todoRepository.save(todoMapper
                .updateTodoFromDto(dto, todoRepository
                        .findById(id)
                        .orElseThrow(() -> new TodoNotFoundException(id))));
    }

    @Override
    @CacheEvict(value = "todosCache", key = "'allTodos'")
    public void delete(Long id) throws TodoNotFoundException {
        todoRepository.delete(todoRepository
                .findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id)));
    }

    public static boolean shouldCache(List<TodoDTO> todos) {
        if (todos != null) {
            return todos.stream().anyMatch(todo -> todo.getId() != null && todo.getId() <= 2);
        }
        return false;
    }
}
