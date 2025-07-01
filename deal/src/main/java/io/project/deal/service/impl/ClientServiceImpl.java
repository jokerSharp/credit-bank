package io.project.deal.service.impl;

import io.project.deal.mapper.ClientMapper;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Passport;
import io.project.deal.repository.ClientRepository;
import io.project.deal.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.project.deal.util.validation.MessageForException.entityIdIsNullMessage;
import static io.project.deal.util.validation.MessageForException.entityNotFoundMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Override
    public Client findById(UUID id) {
        log.info("finding client with id={}", id);
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(entityNotFoundMessage(Client.class, id));
                    return new EntityNotFoundException(entityNotFoundMessage(Client.class, id));
                });
    }

    @Transactional
    @Override
    public Client create(LoanStatementRequestDto LoanStatementRequestDto, Passport passport) {
        log.info("creating client with loan statement={} and passport={}", LoanStatementRequestDto, passport);
        return clientRepository.save(clientMapper.ofLoanStatementRequestDto(LoanStatementRequestDto, passport));
    }

    @Transactional
    @Override
    public Client update(Client client) {
        if (client.getClientId() == null) {
            log.error(entityIdIsNullMessage(Client.class));
            throw new IllegalArgumentException(entityIdIsNullMessage(Client.class));
        }
        log.info("updating client={}", client);
        return clientRepository.save(client);
    }
}
