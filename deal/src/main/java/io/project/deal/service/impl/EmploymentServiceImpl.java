package io.project.deal.service.impl;

import io.project.deal.mapper.EmploymentMapper;
import io.project.deal.model.dto.request.EmploymentDto;
import io.project.deal.model.entity.Employment;
import io.project.deal.repository.EmploymentRepository;
import io.project.deal.service.EmploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmploymentServiceImpl implements EmploymentService {

    private final EmploymentRepository employmentRepository;

    private final EmploymentMapper employmentMapper;

    @Transactional
    @Override
    public Employment create(EmploymentDto employmentDto) {
        Employment employment = employmentMapper.toEntity(employmentDto);
        log.info("creating employment={}", employment);
        return employmentRepository.save(employment);
    }
}
