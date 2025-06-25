package io.project.deal.mapper;

import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.ScoringDataDto;
import io.project.deal.model.entity.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {

    @Mapping(source = "statement.appliedOffer.requestedAmount", target = "amount")
    @Mapping(source = "statement.appliedOffer.term", target = "term")
    @Mapping(source = "statement.client.firstName", target = "firstName")
    @Mapping(source = "statement.client.lastName", target = "lastName")
    @Mapping(source = "statement.client.middleName", target = "middleName")
    @Mapping(source = "finishRegistrationRequestDto.gender", target = "gender")
    @Mapping(source = "statement.client.birthdate", target = "birthdate")
    @Mapping(source = "statement.client.passport.series", target = "passportSeries")
    @Mapping(source = "statement.client.passport.number", target = "passportNumber")
    @Mapping(source = "finishRegistrationRequestDto.passportIssueDate", target = "passportIssueDate")
    @Mapping(source = "finishRegistrationRequestDto.passportIssueBranch", target = "passportIssueBranch")
    @Mapping(source = "finishRegistrationRequestDto.maritalStatus", target = "maritalStatus")
    @Mapping(source = "finishRegistrationRequestDto.dependentAmount", target = "dependentAmount")
    @Mapping(source = "finishRegistrationRequestDto.employment", target = "employment")
    @Mapping(source = "finishRegistrationRequestDto.accountNumber", target = "accountNumber")
    @Mapping(source = "statement.appliedOffer.isInsuranceEnabled", target = "isInsuranceEnabled")
    @Mapping(source = "statement.appliedOffer.isSalaryClient", target = "isSalaryClient")
    ScoringDataDto toScoringDataDto(Statement statement,
                                    FinishRegistrationRequestDto finishRegistrationRequestDto);
}
