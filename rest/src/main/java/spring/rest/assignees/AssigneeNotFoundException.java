package spring.rest.assignees;

import org.apache.coyote.BadRequestException;

public class AssigneeNotFoundException extends BadRequestException {
    public AssigneeNotFoundException(Long id) {
        super(String.format("The assignee with #ID: %d is NOT FOUND!", id));
    }
}
