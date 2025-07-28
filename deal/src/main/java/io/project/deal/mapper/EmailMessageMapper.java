package io.project.deal.mapper;

import io.project.deal.model.dto.request.EmailMessageDto;
import io.project.deal.model.entity.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMessageMapper {

    @Mapping(source = "statement.client.email", target = "address")
    @Mapping(target = "theme", expression = "java(io.project.deal.model.enums.EmailMessageTheme.FINISH_REGISTRATION)")
    @Mapping(source = "message", target = "text")
    EmailMessageDto finishRegistration(Statement statement, String message);

    @Mapping(source = "statement.client.email", target = "address")
    @Mapping(target = "theme", expression = "java(io.project.deal.model.enums.EmailMessageTheme.CREATE_DOCUMENTS)")
    @Mapping(source = "message", target = "text")
    EmailMessageDto createDocuments(Statement statement, String message);

    @Mapping(source = "statement.client.email", target = "address")
    @Mapping(target = "theme", expression = "java(io.project.deal.model.enums.EmailMessageTheme.STATEMENT_DENIED)")
    @Mapping(source = "message", target = "text")
    EmailMessageDto clientDenied(Statement statement, String message);

    @Mapping(source = "statement.client.email", target = "address")
    @Mapping(target = "theme", expression = "java(io.project.deal.model.enums.EmailMessageTheme.SEND_DOCUMENTS)")
    @Mapping(source = "credit", target = "text")
    EmailMessageDto prepareDocuments(Statement statement, String credit);

    @Mapping(source = "statement.client.email", target = "address")
    @Mapping(target = "theme", expression = "java(io.project.deal.model.enums.EmailMessageTheme.SEND_SES)")
    @Mapping(source = "message", target = "text")
    EmailMessageDto sendSesCode(Statement statement, String message);


    @Mapping(source = "statement.client.email", target = "address")
    @Mapping(target = "theme", expression = "java(io.project.deal.model.enums.EmailMessageTheme.CREDIT_ISSUED)")
    @Mapping(source = "message", target = "text")
    EmailMessageDto creditIssued(Statement statement, String message);
}
