package io.project.deal.mapper;

import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.entity.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    Credit toEntity(CreditDto creditDto);

    CreditDto toDto(Credit credit);
}
