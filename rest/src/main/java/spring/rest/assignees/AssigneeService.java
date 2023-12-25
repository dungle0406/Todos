package spring.rest.assignees;

import java.util.List;

public interface AssigneeService {
    List<AssigneeDTO> findAll();

    void addAssignee(AssigneeDTO dto);

    void edit(Long id, AssigneeDTO dto) throws  AssigneeNotFoundException;

    void delete(Long id) throws AssigneeNotFoundException;
}
