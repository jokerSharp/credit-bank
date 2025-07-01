package io.project.deal.service.impl;

import io.project.deal.mapper.StatementMapper;
import io.project.deal.mapper.StatementStatusHistoryMapper;
import io.project.deal.model.dto.response.StatementStatusHistoryDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Credit;
import io.project.deal.model.entity.Statement;
import io.project.deal.model.enums.ApplicationStatus;
import io.project.deal.repository.StatementRepository;
import io.project.deal.service.StatementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static io.project.deal.util.validation.MessageForException.entityIdIsNullMessage;
import static io.project.deal.util.validation.MessageForException.entityNotFoundMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    private final StatementMapper statementMapper;

    private final StatementStatusHistoryMapper statementStatusHistoryMapper;

    @Override
    public Statement findById(UUID id) {
        return statementRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(entityNotFoundMessage(Statement.class, id));
                    return new EntityNotFoundException(entityNotFoundMessage(Statement.class, id));
                });
    }

    @Transactional
    @Override
    public Statement create(Client client) {
        Statement statement = statementMapper.addClient(client);
        log.info("creating statement={}", statement);
        return statementRepository.save(statement);
    }

    @Transactional
    @Override
    public Statement update(Statement statement) {
        if (statement.getStatementId() == null) {
            log.error(entityIdIsNullMessage(Statement.class));
            throw new IllegalArgumentException(entityIdIsNullMessage(Statement.class));
        }
        log.info("updating statement={}", statement);
        return statementRepository.save(statement);
    }

    @Transactional
    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("selecting offer={} for statement", loanOfferDto);
        Statement statement = findById(loanOfferDto.getStatementId());
        statement.setAppliedOffer(loanOfferDto);
        StatementStatusHistoryDto statementStatusHistoryDto = statementStatusHistoryMapper.toDto(statement);
        statement.setStatus(ApplicationStatus.APPROVED);
        statement.setStatusHistory(List.of(statementStatusHistoryDto));
        update(statement);
    }

    @Transactional
    @Override
    public void mergeCreditIntoStatement(String statementId, Credit credit) {
        log.info("merging credit={} into statement={}", credit, statementId);
        Statement statement = findById(UUID.fromString(statementId));
        statement.setCredit(credit);
        StatementStatusHistoryDto statementStatusHistoryDto = statementStatusHistoryMapper.toDto(statement);
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.getStatusHistory().add(statementStatusHistoryDto);
        update(statement);
    }

    @Transactional
    @Override
    public void declineClientAfterScoring(String statementId) {
        log.info("declining client after scoring for statement with id={}", statementId);
        Statement statement = findById(UUID.fromString(statementId));
        StatementStatusHistoryDto statementStatusHistoryDto = statementStatusHistoryMapper.toDto(statement);
        statement.setStatus(ApplicationStatus.CC_DENIED);
        statement.getStatusHistory().add(statementStatusHistoryDto);
        update(statement);
    }
}
