package spring.rest.assignees;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssigneeServiceImpl implements AssigneeService {
    private final AssigneeRepository assigneeRepository;
    private final AssigneeMapper assigneeMapper;

    public AssigneeServiceImpl(AssigneeRepository assigneeRepository, AssigneeMapper assigneeMapper) {
        this.assigneeRepository = assigneeRepository;
        this.assigneeMapper = assigneeMapper;
    }

    @Override
    public void create(AssigneeDTO dto) {
        assigneeRepository.save(assigneeMapper.mapAssigneeFromDto(dto));
    }

    @Override
    public List<AssigneeDTO> findAll(AssigneeDTO dto) {
        return assigneeRepository.findAll(Example.of(assigneeMapper.mapAssigneeFromDto(dto)))
                .stream()
                .map(assigneeMapper::mapDtoFromAssignee)
                .collect(Collectors.toList());
    }

    @Override
    public void edit(Long id, AssigneeDTO dto) throws AssigneeNotFoundException {
        assigneeRepository.save(assigneeMapper
                .updateAssigneeFromDto(dto, assigneeRepository
                        .findById(id)
                        .orElseThrow(() -> new AssigneeNotFoundException(id))));
    }

    @Override
    public void delete(Long id) throws AssigneeNotFoundException {
        assigneeRepository.delete(assigneeRepository
                .findById(id)
                .orElseThrow(() -> new AssigneeNotFoundException(id)));
    }
}
