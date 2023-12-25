package spring.rest.assignees;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import spring.rest.todos.TodoDTO;

import java.util.List;

@Getter
@ToString
@Builder
public class AssigneeDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("todos")
    private List<TodoDTO> todos;
}
