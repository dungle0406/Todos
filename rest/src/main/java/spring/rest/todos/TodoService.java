package spring.rest.todos;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface TodoService {
    void create(TodoDTO dto);

    List<TodoDTO> search(TodoDTO dto) throws BadRequestException, JsonProcessingException;

    List<TodoDTO> findAll(TodoDTO dto) throws BadRequestException, JsonProcessingException;

    void edit(Long id, TodoDTO dto) throws TodoNotFoundException;

    void delete(Long id) throws TodoNotFoundException;

}
