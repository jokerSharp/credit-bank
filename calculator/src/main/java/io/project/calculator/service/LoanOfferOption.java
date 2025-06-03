package io.project.calculator.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanOfferOption {
    NO_INSURANCE_NOT_SALARY_CLIENT(false, false),
    NO_INSURANCE_SALARY_CLIENT(false, true),
    HAVE_INSURANCE_NOT_SALARY_CLIENT(true, false),
    HAVE_INSURANCE_SALARY_CLIENT(true, true);

    private final boolean isInsuranceEnabled;
    private final boolean isSalaryClient;
}
