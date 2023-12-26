package spring.rest.todos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@Builder
public class TodoDTO implements Serializable {
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
}
