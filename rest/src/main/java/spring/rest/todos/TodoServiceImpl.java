package spring.rest.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

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
    public List<TodoDTO> findAll() {
        return todoRepository.findAll().stream()
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
