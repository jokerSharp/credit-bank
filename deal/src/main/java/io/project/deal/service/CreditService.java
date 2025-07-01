package io.project.deal.service;

import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.entity.Credit;

public interface CreditService {
    Credit create(CreditDto creditDto);
}
