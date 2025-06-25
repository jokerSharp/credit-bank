package io.project.deal.mapper;

import io.project.deal.model.dto.request.EmploymentDto;
import io.project.deal.model.entity.Employment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmploymentMapper {

    @Mapping(source = "employmentDto.employmentStatus", target = "status")
    @Mapping(source = "employmentDto.employerINN", target = "employerInn")
    Employment toEntity(EmploymentDto employmentDto);

    EmploymentDto toDto(Employment employment);
}
