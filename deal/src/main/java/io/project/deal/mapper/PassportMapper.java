package io.project.deal.mapper;

import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.entity.Credit;
import io.project.deal.model.entity.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    @Mapping(source = "loanStatementRequestDto.passportNumber", target="number")
    @Mapping(source = "loanStatementRequestDto.passportSeries", target="series")
    Passport addInitialInformation(LoanStatementRequestDto loanStatementRequestDto);

    @Mapping(source = "finishRegistrationRequestDto.passportIssueBranch", target="issueBranch")
    @Mapping(source = "finishRegistrationRequestDto.passportIssueDate", target="issueDate")
    Passport addFinishInformation(Passport originalPassport, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
