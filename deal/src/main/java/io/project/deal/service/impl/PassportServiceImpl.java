package io.project.deal.service.impl;

import io.project.deal.mapper.PassportMapper;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.entity.Passport;
import io.project.deal.repository.PassportRepository;
import io.project.deal.service.PassportService;
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
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapper;

    @Override
    public Passport findById(UUID id) {
        return passportRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(entityNotFoundMessage(Passport.class, id));
                    return new EntityNotFoundException(entityNotFoundMessage(Passport.class, id));
                });
    }

    @Transactional
    @Override
    public Passport create(LoanStatementRequestDto loanStatementRequestDto) {
        Passport passport = passportMapper.addInitialInformation(loanStatementRequestDto);
        log.info("creating passport={}", passport);
        return passportRepository.save(passport);
    }

    @Transactional
    @Override
    public Passport update(Passport passport) {
        if (passport.getPassportId() == null) {
            log.error(entityIdIsNullMessage(Passport.class));
            throw new IllegalArgumentException(entityIdIsNullMessage(Passport.class));
        }
        log.info("updating passport={}", passport);
        return passportRepository.save(passport);
    }
}
