package spring.rest.todos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@Builder
public class TodoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("done")
    private Boolean done;

    @JsonProperty("urgent")
    private Boolean urgent;

    @JsonProperty("assignee")
    private Long assigneeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoDTO todoDTO = (TodoDTO) o;
        return Objects.equals(id, todoDTO.id) && Objects.equals(title, todoDTO.title) && Objects.equals(done, todoDTO.done) && Objects.equals(urgent, todoDTO.urgent) && Objects.equals(assigneeId, todoDTO.assigneeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, done, urgent, assigneeId);
    }
}
