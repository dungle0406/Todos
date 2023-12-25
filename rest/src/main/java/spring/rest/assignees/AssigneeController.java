package spring.rest.assignees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignees")
public class AssigneeController {
    private final AssigneeService assigneeService;

    @Autowired
    public AssigneeController(AssigneeService assigneeService) {
        this.assigneeService = assigneeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAssignee(@RequestBody AssigneeDTO dto) {
        assigneeService.create(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AssigneeDTO> findAllAssignees(@ModelAttribute AssigneeDTO dto) {
        return assigneeService.findAll(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editAssignee(@PathVariable Long id, @RequestBody AssigneeDTO dto) throws AssigneeNotFoundException {
        assigneeService.edit(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAssignee(@PathVariable Long id) throws AssigneeNotFoundException {
        assigneeService.delete(id);
    }
}
