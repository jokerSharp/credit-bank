package io.project.deal.mapper;

import io.project.deal.model.dto.response.StatementStatusHistoryDto;
import io.project.deal.model.entity.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatementStatusHistoryMapper {

    @Mapping(target = "changeType", expression = "java(io.project.deal.model.enums.ChangeType.AUTOMATIC)")
    @Mapping(target = "time", expression = "java(java.time.LocalDateTime.now())")
    StatementStatusHistoryDto toDto(Statement statement);
}
