package io.project.deal.mapper;

import io.project.deal.model.dto.response.LoanOfferDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LoanOfferMapper {

    @Mapping(target = "statementId", source = "statementId")
    LoanOfferDto setStatementIdToOffer(LoanOfferDto loanOfferDto, UUID statementId);

    default List<LoanOfferDto> setStatementIdToOfferList(List<LoanOfferDto> loanOfferDtoList, UUID statementId) {
        if (loanOfferDtoList == null) {
            return null;
        }
        return loanOfferDtoList.stream()
                .map(dto -> setStatementIdToOffer(dto, statementId))
                .collect(Collectors.toList());
    }
}
