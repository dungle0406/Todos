package spring.rest.assignees;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssigneeMapper {
    Assignee mapAssigneeFromDto(AssigneeDTO dto);

    AssigneeDTO mapDtoFromAssignee(Assignee todo);

    Assignee updateAssigneeFromDto(AssigneeDTO dto, @MappingTarget Assignee assignee);
}
