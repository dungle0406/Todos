package spring.rest.assignees;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssigneeService {
    void create(AssigneeDTO dto);

    List<AssigneeDTO> findAll(AssigneeDTO dto);

    void edit(Long id, AssigneeDTO dto) throws AssigneeNotFoundException;

    void delete(Long id) throws AssigneeNotFoundException;
}
