package spring.rest.todos;

import org.apache.coyote.BadRequestException;

public class TodoNotFoundException extends BadRequestException {
    public TodoNotFoundException(Long id) {
        super(String.format("The todo with ID: %d is NOT FOUND!", id));
    }
}
