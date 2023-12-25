package spring.rest.assignees;

import org.apache.coyote.BadRequestException;

public class AssigneeNotFoundException extends BadRequestException {
    public AssigneeNotFoundException(Long id) {
        super(String.format("Assignee with ID: %d is NOT FOUND!", id));
    }
}
