package io.project.calculator.data;

import io.project.calculator.model.dto.enums.EmploymentStatus;
import io.project.calculator.model.dto.enums.Gender;
import io.project.calculator.model.dto.enums.MaritalStatus;
import io.project.calculator.model.dto.enums.Position;
import io.project.calculator.model.dto.request.EmploymentDto;
import io.project.calculator.model.dto.request.LoanStatementRequestDto;
import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.model.dto.response.CreditDto;
import io.project.calculator.model.dto.response.LoanOfferDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CalculatorTestData {

    public static final LoanStatementRequestDto VALID_LOAN_STATEMENT_REQUEST_DTO = LoanStatementRequestDto.builder()
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@mail.com")
            .birthdate(LocalDate.now().minusYears(30))
            .passportSeries("1234")
            .passportNumber("567890")
            .build();

    public static final LoanStatementRequestDto INVALID_LOAN_STATEMENT_REQUEST_DTO = LoanStatementRequestDto.builder()
            .amount(BigDecimal.valueOf(100))
            .term(5)
            .firstName("")
            .lastName("")
            .email("john.doe")
            .birthdate(LocalDate.now().minusYears(10))
            .passportSeries("")
            .passportNumber("")
            .build();

    public static final List<LoanOfferDto> OFFERS = List.of(
            LoanOfferDto.builder().statementId(UUID.randomUUID()).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(168714.00)).term(10).monthlyPayment(BigDecimal.valueOf(1405.95))
                    .rate(BigDecimal.valueOf(11.5)).isInsuranceEnabled(false).isSalaryClient(false).build(),
            LoanOfferDto.builder().statementId(UUID.randomUUID()).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(165980.40)).term(10).monthlyPayment(BigDecimal.valueOf(1383.17))
                    .rate(BigDecimal.valueOf(11.1)).isInsuranceEnabled(false).isSalaryClient(true).build(),
            LoanOfferDto.builder().statementId(UUID.randomUUID()).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(164622.00)).term(10).monthlyPayment(BigDecimal.valueOf(1371.85))
                    .rate(BigDecimal.valueOf(10.9)).isInsuranceEnabled(true).isSalaryClient(false).build(),
            LoanOfferDto.builder().statementId(UUID.randomUUID()).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(161922.00)).term(10).monthlyPayment(BigDecimal.valueOf(1349.35))
                    .rate(BigDecimal.valueOf(10.5)).isInsuranceEnabled(true).isSalaryClient(true).build()
    );

    public static final EmploymentDto EMPLOYMENT_DTO_1 = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(5000))
            .position(Position.MIDDLE_MANAGER)
            .workExperienceTotal(36)
            .workExperienceCurrent(18)
            .build();

    public static final EmploymentDto EMPLOYMENT_DTO_2 = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.BUSINESS_OWNER)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(10000))
            .position(Position.TOP_MANAGER)
            .workExperienceTotal(24)
            .workExperienceCurrent(6)
            .build();

    public static final EmploymentDto EMPLOYMENT_DTO_3 = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(6050))
            .position(Position.LINEAR_EMPLOYEE)
            .workExperienceTotal(50)
            .workExperienceCurrent(50)
            .build();

    public static final EmploymentDto INSUFFICIENT_SALARY_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(1))
            .position(Position.MIDDLE_MANAGER)
            .workExperienceTotal(36)
            .workExperienceCurrent(18)
            .build();

    public static final EmploymentDto UNEMPLOYED_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.UNEMPLOYED)
            .build();

    public static final EmploymentDto INSUFFICIENT_TOTAL_EXPERIENCE_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(6050))
            .position(Position.LINEAR_EMPLOYEE)
            .workExperienceTotal(17)
            .workExperienceCurrent(17)
            .build();

    public static final EmploymentDto INSUFFICIENT_CURRENT_EXPERIENCE_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(6050))
            .position(Position.LINEAR_EMPLOYEE)
            .workExperienceTotal(50)
            .workExperienceCurrent(1)
            .build();

    public static final ScoringDataDto SCORING_DATA_DTO_1 = getScoringDataDtoWithDifferentEmployment(EMPLOYMENT_DTO_1);

    public static final ScoringDataDto SCORING_DATA_DTO_2 = ScoringDataDto.builder()
            .amount(BigDecimal.valueOf(200000))
            .term(20)
            .firstName("Joan")
            .lastName("Doe")
            .gender(Gender.FEMALE)
            .birthdate(LocalDate.now().minusYears(35))
            .passportSeries("1234")
            .passportNumber("567890")
            .passportIssueDate(LocalDate.now().minusYears(2))
            .passportIssueBranch("Franzgasse 47")
            .maritalStatus(MaritalStatus.MARRIED)
            .dependentAmount(1)
            .employment(EMPLOYMENT_DTO_2)
            .accountNumber("12345678901234567890")
            .isInsuranceEnabled(false)
            .isSalaryClient(true)
            .build();

    public static final ScoringDataDto SCORING_DATA_DTO_3 = ScoringDataDto.builder()
            .amount(BigDecimal.valueOf(120000))
            .term(12)
            .firstName("Magdalene")
            .lastName("Chante")
            .gender(Gender.NON_BINARY)
            .birthdate(LocalDate.now().minusYears(40))
            .passportSeries("1234")
            .passportNumber("567890")
            .passportIssueDate(LocalDate.now().minusYears(5))
            .passportIssueBranch("50 Jln Ghazali Jawi")
            .maritalStatus(MaritalStatus.SINGLE)
            .dependentAmount(1)
            .employment(EMPLOYMENT_DTO_3)
            .accountNumber("12345678901234567890")
            .isInsuranceEnabled(true)
            .isSalaryClient(true)
            .build();

    public static final ScoringDataDto INVALID_SCORING_DATA_DTO = ScoringDataDto.builder()
            .amount(BigDecimal.valueOf(0))
            .build();

    public static final ScoringDataDto UNEMPLOYED_SCORING_DATA_DTO =
            getScoringDataDtoWithDifferentEmployment(UNEMPLOYED_EMPLOYMENT_DTO);

    public static final ScoringDataDto HIGH_AMOUNT_SCORING_DATA_DTO =
            getScoringDataDtoWithDifferentEmployment(INSUFFICIENT_SALARY_EMPLOYMENT_DTO);

    public static final ScoringDataDto INSUFFICIENT_TOTAL_EXPERIENCE_SCORING_DATA_DTO =
            getScoringDataDtoWithDifferentEmployment(INSUFFICIENT_TOTAL_EXPERIENCE_EMPLOYMENT_DTO);

    public static final ScoringDataDto INSUFFICIENT_CURRENT_EXPERIENCE_SCORING_DATA_DTO =
            getScoringDataDtoWithDifferentEmployment(INSUFFICIENT_CURRENT_EXPERIENCE_EMPLOYMENT_DTO);

    public static final ScoringDataDto TOO_YOUNG_SCORING_DATA_DTO = ScoringDataDto.builder()
            .amount(BigDecimal.valueOf(200000))
            .term(20)
            .firstName("Joan")
            .lastName("Doe")
            .gender(Gender.FEMALE)
            .birthdate(LocalDate.now().minusYears(19))
            .passportSeries("1234")
            .passportNumber("567890")
            .passportIssueDate(LocalDate.now().minusYears(2))
            .passportIssueBranch("Franzgasse 47")
            .maritalStatus(MaritalStatus.MARRIED)
            .dependentAmount(1)
            .employment(EMPLOYMENT_DTO_2)
            .accountNumber("12345678901234567890")
            .isInsuranceEnabled(false)
            .isSalaryClient(true)
            .build();

    public static final ScoringDataDto TOO_OLD_SCORING_DATA_DTO = ScoringDataDto.builder()
            .amount(BigDecimal.valueOf(200000))
            .term(20)
            .firstName("Joan")
            .lastName("Doe")
            .gender(Gender.FEMALE)
            .birthdate(LocalDate.now().minusYears(66))
            .passportSeries("1234")
            .passportNumber("567890")
            .passportIssueDate(LocalDate.now().minusYears(2))
            .passportIssueBranch("Franzgasse 47")
            .maritalStatus(MaritalStatus.MARRIED)
            .dependentAmount(1)
            .employment(EMPLOYMENT_DTO_2)
            .accountNumber("12345678901234567890")
            .isInsuranceEnabled(false)
            .isSalaryClient(true)
            .build();

    public static final CreditDto CREDIT_DTO_1 = CreditDto.builder()
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .monthlyPayment(BigDecimal.valueOf(1208.00))
            .rate(BigDecimal.valueOf(7.9))
            .psk(BigDecimal.valueOf(144960.00))
            .isInsuranceEnabled(true)
            .isSalaryClient(false)
            .paymentSchedule(List.of())
            .build();

    public static ScoringDataDto getScoringDataDtoWithDifferentEmployment(EmploymentDto employmentDto) {
        return ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(10)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .birthdate(LocalDate.now().minusYears(40))
                .passportSeries("1234")
                .passportNumber("567890")
                .passportIssueDate(LocalDate.now().minusYears(10))
                .passportIssueBranch("Fleetwood st. 123")
                .maritalStatus(MaritalStatus.DIVORCED)
                .dependentAmount(1)
                .employment(employmentDto)
                .accountNumber("12345678901234567890")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
    }
}
