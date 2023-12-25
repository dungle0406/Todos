package spring.rest.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewTodo(@RequestBody TodoDTO dto) {
        todoService.create(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TodoDTO> findAllTodos() {
        return todoService.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTodo(@PathVariable Long id, @RequestBody TodoDTO dto) throws TodoNotFoundException {
        todoService.edit(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws TodoNotFoundException {
        todoService.delete(id);
    }
}
