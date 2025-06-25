package io.project.deal.mapper;

import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Credit;
import io.project.deal.model.entity.Employment;
import io.project.deal.model.entity.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client ofLoanStatementRequestDto(LoanStatementRequestDto LoanStatementRequestDto, Passport passport);

    @Mapping(source = "employment", target = "employment")
    @Mapping(source = "finishRegistrationRequestDto.gender", target = "gender")
    @Mapping(source = "finishRegistrationRequestDto.maritalStatus", target = "maritalStatus")
    @Mapping(source = "finishRegistrationRequestDto.dependentAmount", target = "dependentAmount")
    @Mapping(source = "finishRegistrationRequestDto.accountNumber", target = "accountNumber")
    Client addFinishInformation(Client originalClient, Employment employment, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
