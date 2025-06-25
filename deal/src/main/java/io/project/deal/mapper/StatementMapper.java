package io.project.deal.mapper;

import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatementMapper {

    @Mapping(target = "status", expression = "java(io.project.deal.model.enums.ApplicationStatus.PREAPPROVAL)")
    Statement addClient(Client client);
}
