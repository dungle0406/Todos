package spring.rest.todos;

import ch.qos.logback.core.model.ComponentModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo mapTodoFromDto(TodoDTO dto);

    TodoDTO mapDtoFromTodo(Todo todo);
}
