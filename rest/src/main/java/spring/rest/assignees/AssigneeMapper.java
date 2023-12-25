package spring.rest.assignees;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssigneeMapper {
    Assignee mapAssigneeFromDto(AssigneeDTO dto);

    AssigneeDTO mapDtoFromAssignee(Assignee assignee);

    Assignee updateAssigneeFromDto(AssigneeDTO dto, @MappingTarget Assignee assignee);
}
