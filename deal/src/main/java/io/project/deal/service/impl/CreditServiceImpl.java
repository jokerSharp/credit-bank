package io.project.deal.service.impl;

import io.project.deal.mapper.CreditMapper;
import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.entity.Credit;
import io.project.deal.model.enums.CreditStatus;
import io.project.deal.repository.CreditRepository;
import io.project.deal.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    @Transactional
    @Override
    public Credit create(CreditDto creditDto) {
        Credit credit = creditMapper.toEntity(creditDto);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        log.info("creating credit={}", credit);
        return creditRepository.save(credit);
    }
}
