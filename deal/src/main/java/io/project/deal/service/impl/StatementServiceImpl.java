package io.project.deal.service.impl;

import io.project.deal.exception.LoanRequestDeniedException;
import io.project.deal.mapper.EmailMessageMapper;
import io.project.deal.mapper.StatementMapper;
import io.project.deal.mapper.StatementStatusHistoryMapper;
import io.project.deal.model.dto.request.EmailMessageDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.dto.response.StatementStatusHistoryDto;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Credit;
import io.project.deal.model.entity.Statement;
import io.project.deal.model.enums.ApplicationStatus;
import io.project.deal.repository.StatementRepository;
import io.project.deal.service.CreditService;
import io.project.deal.service.EmailMessageProducer;
import io.project.deal.service.StatementService;
import io.project.deal.util.EmailMessageUtils;
import io.project.deal.util.SesCodeUtils;
import io.project.deal.util.serialization.CreditSerializer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static io.project.deal.util.validation.MessageForException.entityIdIsNullMessage;
import static io.project.deal.util.validation.MessageForException.entityNotFoundMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatementServiceImpl implements StatementService {

    @Value("${app.finish-registration.url}")
    private String finishRegistrationUrl;

    @Value("${app.create-documents.url}")
    private String createDocumentsUrl;

    @Value("${app.send-code.url}")
    private String sendCodeUrl;

    private final CreditService creditService;

    private final StatementRepository statementRepository;

    private final StatementMapper statementMapper;

    private final StatementStatusHistoryMapper statementStatusHistoryMapper;

    private final EmailMessageMapper emailMessageMapper;

    private final EmailMessageProducer emailMessageProducer;

    private final CreditSerializer creditSerializer;

    @Override
    public Statement findById(UUID id) {
        return statementRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(entityNotFoundMessage(Statement.class, id));
                    return new EntityNotFoundException(entityNotFoundMessage(Statement.class, id));
                });
    }

    @Override
    public List<Statement> finalAll() {
        return StreamSupport.stream(statementRepository.findAll().spliterator(), false)
                .toList();
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
        String message = EmailMessageUtils.finishRegistrationMailMessage(finishRegistrationUrl);
        emailMessageProducer.sendMessage(EmailMessageProducer.FINISH_REGISTRATION_TOPIC,
                emailMessageMapper.finishRegistration(statement, message));
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
        String message = EmailMessageUtils.createDocumentsMailMessage(createDocumentsUrl);
        emailMessageProducer.sendMessage(EmailMessageProducer.CREATE_DOCUMENTS_TOPIC,
                emailMessageMapper.createDocuments(statement, message));
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
        String message = EmailMessageUtils.statementDeniedMailMessage();
        emailMessageProducer.sendMessage(EmailMessageProducer.STATEMENT_DENIED_TOPIC,
                emailMessageMapper.clientDenied(statement, message));
    }

    @Transactional
    @Override
    public void prepareDocuments(String statementId) {
        log.info("preparing documents for statement with id={}", statementId);
        Statement statement = findById(UUID.fromString(statementId));
        statement.getStatusHistory().add(statementStatusHistoryMapper.toDto(statement));
        statement.setStatus(ApplicationStatus.PREPARE_DOCUMENTS);
        update(statement);
        emailMessageProducer.sendMessage(EmailMessageProducer.SEND_DOCUMENTS_TOPIC,
                emailMessageMapper.prepareDocuments(statement, creditSerializer.serialize(statement.getCredit())));
        statement.getStatusHistory().add(statementStatusHistoryMapper.toDto(statement));
        statement.setStatus(ApplicationStatus.DOCUMENT_CREATED);
    }

    @Transactional
    @Override
    public void signDocuments(String statementId) {
        Statement statement = findById(UUID.fromString(statementId));
        String code = SesCodeUtils.generateSesCode();
        statement.setSesCode(code);
        log.info("update statement with id={} with code={}", statementId, code);
        update(statement);
        String message = EmailMessageUtils.sesCodeMailMessage(code, sendCodeUrl);
        log.info("sending message={} to the topic={}", message, EmailMessageProducer.SEND_SES_TOPIC);
        emailMessageProducer.sendMessage(EmailMessageProducer.SEND_SES_TOPIC,
                emailMessageMapper.sendSesCode(statement, message));
    }

    @Transactional
    @Override
    public void verifySesCode(String statementId, EmailMessageDto emailMessageDto) {
        Statement statement = findById(UUID.fromString(statementId));
        if (statement.getSesCode().equals(emailMessageDto.getText())) {
            log.info("sign statement with id={} with security code={}", statementId, emailMessageDto.getText());
            statement.getStatusHistory().add(statementStatusHistoryMapper.toDto(statement));
            statement.setStatus(ApplicationStatus.DOCUMENT_SIGNED);
            statement.setSignDate(LocalDateTime.now());
            creditService.update(statement.getCredit());
            statement.getStatusHistory().add(statementStatusHistoryMapper.toDto(statement));
            statement.setStatus(ApplicationStatus.CREDIT_ISSUED);
            update(statement);
            log.debug("sending message to the topic={}", EmailMessageProducer.CREDIT_ISSUED_TOPIC);
            emailMessageProducer.sendMessage(EmailMessageProducer.CREDIT_ISSUED_TOPIC,
                    emailMessageMapper.creditIssued(statement, EmailMessageUtils.creditIssuedMailMessage()));
        } else {
            log.error("wrong security code={} for statement with id={}", emailMessageDto.getText(), statementId);
            throw new LoanRequestDeniedException(EmailMessageUtils.wrongCodeMailMessage());
        }
    }
}
