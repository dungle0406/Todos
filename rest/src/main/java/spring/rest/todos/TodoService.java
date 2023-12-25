package spring.rest.todos;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TodoService {
    void create(TodoDTO dto);

    List<TodoDTO> findAll(TodoDTO dto);

    void edit(Long id, TodoDTO dto) throws TodoNotFoundException;

    void delete(Long id) throws TodoNotFoundException;
}
