package io.project.deal.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.project.deal.model.dto.response.PaymentScheduleElementDto;
import io.project.deal.model.enums.CreditStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString(exclude = "paymentSchedule")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit")
@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "credit_id")
    private UUID creditId;
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;
    @NotNull
    @Column(name = "term")
    private Integer term;
    @NotNull
    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;
    @NotNull
    @Column(name = "rate")
    private BigDecimal rate;
    @NotNull
    @Column(name = "psk")
    private BigDecimal psk;
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payment_schedule")
    private List<PaymentScheduleElementDto> paymentSchedule;
    @NotNull
    @Column(name = "insurance_enabled")
    private Boolean isInsuranceEnabled;
    @NotNull
    @Column(name = "salary_client")
    private Boolean isSalaryClient;
    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status")
    private CreditStatus creditStatus;
}
