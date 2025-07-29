package io.project.deal.data;

import io.project.deal.model.dto.request.*;
import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.dto.response.StatementStatusHistoryDto;
import io.project.deal.model.entity.*;
import io.project.deal.model.enums.*;
import io.project.deal.util.EmailMessageUtils;
import io.project.deal.util.SesCodeUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DealTestData {

    public static final UUID STATEMENT_ID = UUID.randomUUID();
    public static final UUID CLIENT_ID = UUID.randomUUID();
    public static final UUID PASSPORT_ID = UUID.randomUUID();
    public static final UUID CREDIT_ID = UUID.randomUUID();
    public static final UUID EMPLOYMENT_ID = UUID.randomUUID();
    public static final String PASSPORT_SERIES = "1234";
    public static final String PASSPORT_NUMBER = "567890";
    public static final String PASSPORT_ISSUE_BRANCH = "Fleetwood st. 123";
    public static final LocalDate PASSPORT_ISSUE_DATE = LocalDate.now().minusYears(10);
    public static final String SES_CODE = "654321";
    public static final String STUB_URL = "localhost:8081";
    public static final String SES_MAIL_MESSAGE = """
            Уважаемый клиент!
            Код для подтверждения вашей заявки 654321
            Его необходимо ввести по адресу localhost:8081
            С уважением, Ваш Банк!""";

    public static final LoanStatementRequestDto VALID_LOAN_STATEMENT_REQUEST_DTO = LoanStatementRequestDto.builder()
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@mail.com")
            .birthdate(LocalDate.now().minusYears(30))
            .passportSeries(PASSPORT_SERIES)
            .passportNumber(PASSPORT_NUMBER)
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

    public static final List<LoanOfferDto> INITIAL_OFFERS = List.of(
            LoanOfferDto.builder().requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(168714.00)).term(10).monthlyPayment(BigDecimal.valueOf(1405.95))
                    .rate(BigDecimal.valueOf(11.5)).isInsuranceEnabled(false).isSalaryClient(false).build(),
            LoanOfferDto.builder().requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(165980.40)).term(10).monthlyPayment(BigDecimal.valueOf(1383.17))
                    .rate(BigDecimal.valueOf(11.1)).isInsuranceEnabled(false).isSalaryClient(true).build(),
            LoanOfferDto.builder().requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(164622.00)).term(10).monthlyPayment(BigDecimal.valueOf(1371.85))
                    .rate(BigDecimal.valueOf(10.9)).isInsuranceEnabled(true).isSalaryClient(false).build(),
            LoanOfferDto.builder().requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(161922.00)).term(10).monthlyPayment(BigDecimal.valueOf(1349.35))
                    .rate(BigDecimal.valueOf(10.5)).isInsuranceEnabled(true).isSalaryClient(true).build()
    );

    public static final List<LoanOfferDto> OFFERS_WITH_STATEMENT = List.of(
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(168714.00)).term(10).monthlyPayment(BigDecimal.valueOf(1405.95))
                    .rate(BigDecimal.valueOf(11.5)).isInsuranceEnabled(false).isSalaryClient(false).build(),
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(165980.40)).term(10).monthlyPayment(BigDecimal.valueOf(1383.17))
                    .rate(BigDecimal.valueOf(11.1)).isInsuranceEnabled(false).isSalaryClient(true).build(),
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(164622.00)).term(10).monthlyPayment(BigDecimal.valueOf(1371.85))
                    .rate(BigDecimal.valueOf(10.9)).isInsuranceEnabled(true).isSalaryClient(false).build(),
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(161922.00)).term(10).monthlyPayment(BigDecimal.valueOf(1349.35))
                    .rate(BigDecimal.valueOf(10.5)).isInsuranceEnabled(true).isSalaryClient(true).build()
    );

    public static final EmploymentDto EMPLOYMENT_DTO_1 = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(5000))
            .position(Position.MID_MANAGER)
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

    public static final Employment UNSAVED_EMPLOYMENT_ENTITY_2 = Employment.builder()
            .status(EmploymentStatus.BUSINESS_OWNER)
            .employerInn("123456789012")
            .salary(BigDecimal.valueOf(10000))
            .position(Position.TOP_MANAGER)
            .workExperienceTotal(24)
            .workExperienceCurrent(6)
            .build();

    public static final Employment SAVED_EMPLOYMENT_ENTITY_2 = Employment.builder()
            .employmentId(EMPLOYMENT_ID)
            .status(EmploymentStatus.BUSINESS_OWNER)
            .employerInn("123456789012")
            .salary(BigDecimal.valueOf(10000))
            .position(Position.TOP_MANAGER)
            .workExperienceTotal(24)
            .workExperienceCurrent(6)
            .build();

    public static final EmploymentDto EMPLOYMENT_DTO_3 = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(6050))
            .position(Position.WORKER)
            .workExperienceTotal(50)
            .workExperienceCurrent(50)
            .build();

    public static final EmploymentDto INSUFFICIENT_SALARY_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(1))
            .position(Position.MID_MANAGER)
            .workExperienceTotal(36)
            .workExperienceCurrent(18)
            .build();

    public static final EmploymentDto UNEMPLOYED_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.UNEMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(10000))
            .position(Position.TOP_MANAGER)
            .workExperienceTotal(24)
            .workExperienceCurrent(6)
            .build();

    public static final Employment UNEMPLOYED_EMPLOYMENT_ENTITY = Employment.builder()
            .status(EmploymentStatus.UNEMPLOYED)
            .employerInn("123456789012")
            .salary(BigDecimal.valueOf(10000))
            .position(Position.TOP_MANAGER)
            .workExperienceTotal(24)
            .workExperienceCurrent(6)
            .build();

    public static final EmploymentDto INSUFFICIENT_TOTAL_EXPERIENCE_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(6050))
            .position(Position.WORKER)
            .workExperienceTotal(17)
            .workExperienceCurrent(17)
            .build();

    public static final EmploymentDto INSUFFICIENT_CURRENT_EXPERIENCE_EMPLOYMENT_DTO = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
            .employerINN("123456789012")
            .salary(BigDecimal.valueOf(6050))
            .position(Position.WORKER)
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
            .passportSeries(PASSPORT_SERIES)
            .passportNumber(PASSPORT_NUMBER)
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
            .passportSeries(PASSPORT_SERIES)
            .passportNumber(PASSPORT_NUMBER)
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
            .passportSeries(PASSPORT_SERIES)
            .passportNumber(PASSPORT_NUMBER)
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
            .passportSeries(PASSPORT_SERIES)
            .passportNumber(PASSPORT_NUMBER)
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

    public static final Credit INITIAL_CREDIT_ENTITY_1 = Credit.builder()
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .monthlyPayment(BigDecimal.valueOf(1208.00))
            .rate(BigDecimal.valueOf(7.9))
            .psk(BigDecimal.valueOf(144960.00))
            .isInsuranceEnabled(true)
            .isSalaryClient(false)
            .paymentSchedule(List.of())
            .build();

    public static final Credit SAVED_CREDIT_ENTITY_1 = Credit.builder()
            .creditId(CREDIT_ID)
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .monthlyPayment(BigDecimal.valueOf(1208.00))
            .rate(BigDecimal.valueOf(7.9))
            .psk(BigDecimal.valueOf(144960.00))
            .isInsuranceEnabled(true)
            .isSalaryClient(false)
            .paymentSchedule(List.of())
            .creditStatus(CreditStatus.CALCULATED)
            .build();

    public static final Credit ISSUED_CREDIT_ENTITY_1 = Credit.builder()
            .creditId(CREDIT_ID)
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .monthlyPayment(BigDecimal.valueOf(1208.00))
            .rate(BigDecimal.valueOf(7.9))
            .psk(BigDecimal.valueOf(144960.00))
            .isInsuranceEnabled(true)
            .isSalaryClient(false)
            .paymentSchedule(List.of())
            .creditStatus(CreditStatus.ISSUED)
            .build();

    public static final String SERIALIZED_SAVED_CREDIT_ENTITY_1 = "{\"creditId\":\"031dcb19-5673-44cc-a7c8-03d6a164cf96\",\"amount\":100000,\"term\":10,\"monthlyPayment\":1208.0,\"rate\":7.9,\"psk\":144960.0,\"paymentSchedule\":[],\"isInsuranceEnabled\":true,\"isSalaryClient\":false,\"creditStatus\":\"CALCULATED\"}";

    public static ScoringDataDto getScoringDataDtoWithDifferentEmployment(EmploymentDto employmentDto) {
        return ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(10)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .birthdate(LocalDate.now().minusYears(40))
                .passportSeries(PASSPORT_SERIES)
                .passportNumber(PASSPORT_NUMBER)
                .passportIssueDate(PASSPORT_ISSUE_DATE)
                .passportIssueBranch(PASSPORT_ISSUE_BRANCH)
                .maritalStatus(MaritalStatus.DIVORCED)
                .dependentAmount(1)
                .employment(employmentDto)
                .accountNumber("12345678901234567890")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
    }

    public static final FinishRegistrationRequestDto VALID_FINISH_REGISTRATION_REQUEST_DTO = FinishRegistrationRequestDto.builder()
            .gender(Gender.FEMALE)
            .maritalStatus(MaritalStatus.MARRIED)
            .dependentAmount(1)
            .passportIssueDate(LocalDate.now().minusYears(1))
            .passportIssueBranch(PASSPORT_ISSUE_BRANCH)
            .employment(EMPLOYMENT_DTO_2)
            .accountNumber("12345678901234567890")
            .build();

    public static final FinishRegistrationRequestDto UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO = FinishRegistrationRequestDto.builder()
            .gender(Gender.FEMALE)
            .maritalStatus(MaritalStatus.MARRIED)
            .dependentAmount(1)
            .passportIssueDate(LocalDate.now().minusYears(1))
            .passportIssueBranch(PASSPORT_ISSUE_BRANCH)
            .employment(UNEMPLOYED_EMPLOYMENT_DTO)
            .accountNumber("12345678901234567890")
            .build();

    public static final Passport UNSAVED_PASSPORT = Passport.builder()
            .series(PASSPORT_SERIES)
            .number(PASSPORT_NUMBER)
            .build();

    public static final Passport SAVED_PASSPORT = Passport.builder()
            .passportId(PASSPORT_ID)
            .series(PASSPORT_SERIES)
            .number(PASSPORT_NUMBER)
            .build();

    public static final Passport FINISHED_PASSPORT = Passport.builder()
            .passportId(PASSPORT_ID)
            .series(PASSPORT_SERIES)
            .number(PASSPORT_NUMBER)
            .issueBranch(PASSPORT_ISSUE_BRANCH)
            .issueDate(PASSPORT_ISSUE_DATE)
            .build();

    public static final Client UNSAVED_CLIENT_ENTITY = Client.builder()
            .firstName(VALID_LOAN_STATEMENT_REQUEST_DTO.getFirstName())
            .lastName(VALID_LOAN_STATEMENT_REQUEST_DTO.getLastName())
            .email(VALID_LOAN_STATEMENT_REQUEST_DTO.getEmail())
            .birthdate(VALID_LOAN_STATEMENT_REQUEST_DTO.getBirthdate())
            .passport(SAVED_PASSPORT)
            .build();

    public static final Client SAVED_CLIENT_ENTITY = Client.builder()
            .clientId(CLIENT_ID)
            .firstName(VALID_LOAN_STATEMENT_REQUEST_DTO.getFirstName())
            .lastName(VALID_LOAN_STATEMENT_REQUEST_DTO.getLastName())
            .email(VALID_LOAN_STATEMENT_REQUEST_DTO.getEmail())
            .birthdate(VALID_LOAN_STATEMENT_REQUEST_DTO.getBirthdate())
            .passport(SAVED_PASSPORT)
            .build();

    public static final Client UPDATED_CLIENT_ENTITY = Client.builder()
            .clientId(CLIENT_ID)
            .firstName(VALID_LOAN_STATEMENT_REQUEST_DTO.getFirstName())
            .lastName(VALID_LOAN_STATEMENT_REQUEST_DTO.getLastName())
            .email(VALID_LOAN_STATEMENT_REQUEST_DTO.getEmail())
            .birthdate(VALID_LOAN_STATEMENT_REQUEST_DTO.getBirthdate())
            .passport(SAVED_PASSPORT)
            .employment(SAVED_EMPLOYMENT_ENTITY_2)
            .build();

    public static final Statement UNSAVED_STATEMENT_ENTITY = Statement.builder()
            .client(SAVED_CLIENT_ENTITY)
            .status(ApplicationStatus.PREAPPROVAL)
            .creationDate(LocalDateTime.now())
            .build();

    public static final Statement SAVED_STATEMENT_ENTITY = Statement.builder()
            .statementId(STATEMENT_ID)
            .client(SAVED_CLIENT_ENTITY)
            .status(ApplicationStatus.PREAPPROVAL)
            .creationDate(LocalDateTime.now())
            .build();

    public static final StatementStatusHistoryDto STATEMENT_STATUS_HISTORY_DTO = StatementStatusHistoryDto.builder()
            .status(ApplicationStatus.PREAPPROVAL)
            .changeType(ChangeType.AUTOMATIC)
            .time(LocalDateTime.now())
            .build();

    public static final Statement APPROVED_STATEMENT_ENTITY = Statement.builder()
            .statementId(STATEMENT_ID)
            .client(SAVED_CLIENT_ENTITY)
            .creationDate(LocalDateTime.now())
            .status(ApplicationStatus.APPROVED)
            .appliedOffer(OFFERS_WITH_STATEMENT.getFirst())
            .statusHistory(new ArrayList<>() {{
                add(STATEMENT_STATUS_HISTORY_DTO);
            }})
            .build();

    public static final Statement WITH_CREDIT_STATEMENT_ENTITY = Statement.builder()
            .statementId(STATEMENT_ID)
            .client(SAVED_CLIENT_ENTITY)
            .status(ApplicationStatus.PREAPPROVAL)
            .creationDate(LocalDateTime.now())
            .status(ApplicationStatus.CC_APPROVED)
            .appliedOffer(OFFERS_WITH_STATEMENT.getFirst())
            .statusHistory(new ArrayList<>() {{
                add(STATEMENT_STATUS_HISTORY_DTO);
            }})
            .credit(SAVED_CREDIT_ENTITY_1)
            .build();

    public static final Statement WITH_CODE_STATEMENT_ENTITY = Statement.builder()
            .statementId(STATEMENT_ID)
            .client(SAVED_CLIENT_ENTITY)
            .status(ApplicationStatus.PREAPPROVAL)
            .creationDate(LocalDateTime.now())
            .status(ApplicationStatus.CC_APPROVED)
            .appliedOffer(OFFERS_WITH_STATEMENT.getFirst())
            .statusHistory(new ArrayList<>() {{
                add(STATEMENT_STATUS_HISTORY_DTO);
            }})
            .credit(SAVED_CREDIT_ENTITY_1)
            .sesCode(SES_CODE)
            .build();

    public static final Statement DECLINED_STATEMENT_ENTITY = Statement.builder()
            .statementId(STATEMENT_ID)
            .client(SAVED_CLIENT_ENTITY)
            .status(ApplicationStatus.PREAPPROVAL)
            .creationDate(LocalDateTime.now())
            .status(ApplicationStatus.CC_DENIED)
            .appliedOffer(OFFERS_WITH_STATEMENT.getFirst())
            .statusHistory(new ArrayList<>() {{
                add(STATEMENT_STATUS_HISTORY_DTO);
            }})
            .build();

    public static final EmailMessageDto CREDIT_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.SEND_DOCUMENTS)
            .address(VALID_LOAN_STATEMENT_REQUEST_DTO.getEmail())
            .text(SERIALIZED_SAVED_CREDIT_ENTITY_1)
            .build();

    public static final EmailMessageDto SES_CODE_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.SEND_SES)
            .address(VALID_LOAN_STATEMENT_REQUEST_DTO.getEmail())
            .text(SES_MAIL_MESSAGE)
            .build();

    public static final EmailMessageDto ONLY_SES_CODE_REQUEST_MESSAGE = EmailMessageDto.builder()
            .text(SES_CODE)
            .build();

    public static final EmailMessageDto RANDOM_SES_CODE_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.SEND_SES)
            .address(VALID_LOAN_STATEMENT_REQUEST_DTO.getEmail())
            .text(EmailMessageUtils.sesCodeMailMessage(SesCodeUtils.generateSesCode(), STUB_URL))
            .build();

    public static final EmailMessageDto CREDIT_ISSUED_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.CREDIT_ISSUED)
            .address(VALID_LOAN_STATEMENT_REQUEST_DTO.getEmail())
            .text(EmailMessageUtils.creditIssuedMailMessage())
            .build();
}
