package spring.rest.todos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class TodoDTO {
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
