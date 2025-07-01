package io.project.deal.model.entity;

import io.project.deal.model.enums.Gender;
import io.project.deal.model.enums.MaritalStatus;
import io.project.deal.util.validation.group.NotBlankValidationGroup;
import io.project.deal.util.validation.group.PatternValidationGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

import static io.project.deal.util.validation.MessageForRequestUtil.*;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private UUID clientId;
    @NotBlank(message = FIRST_NAME_EMPTY, groups = NotBlankValidationGroup.class)
    @Size(min = 2, max = 30, message = FIRST_NAME_FORMAT, groups = PatternValidationGroup.class)
    @Column(name = "first_name")
    private String firstName;
    @NotBlank(message = LAST_NAME_EMPTY, groups = NotBlankValidationGroup.class)
    @Size(min = 2, max = 30, message = LAST_NAME_FORMAT, groups = PatternValidationGroup.class)
    @Column(name = "last_name")
    private String lastName;
    @Size(min = 2, max = 30, message = MIDDLE_NAME_FORMAT, groups = PatternValidationGroup.class)
    @Column(name = "middle_name")
    private String middleName;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birth_date")
    private LocalDate birthdate;
    @NotBlank(message = MAIL_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = MAIL_PATTERN, message = MAIL_FORMAT, groups = PatternValidationGroup.class)
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;
    @Column(name = "dependent_amount")
    private Integer dependentAmount;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id", referencedColumnName = "passport_id")
    private Passport passport;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", referencedColumnName = "employment_id")
    private Employment employment;
    @Pattern(regexp = ACCOUNT_NUMBER_PATTERN, message = ACCOUNT_NUMBER_FORMAT, groups = PatternValidationGroup.class)
    @Column(name = "account_number")
    private String accountNumber;
}
