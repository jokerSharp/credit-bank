package io.project.dossier.model.entity;

import io.project.dossier.model.dto.response.PaymentScheduleElementDto;
import io.project.dossier.model.enums.CreditStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ToString(exclude = "paymentSchedule")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    private UUID creditId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Integer term;
    @NotNull
    private BigDecimal monthlyPayment;
    @NotNull
    private BigDecimal rate;
    @NotNull
    private BigDecimal psk;
    @NotNull
    private List<PaymentScheduleElementDto> paymentSchedule;
    @NotNull
    private Boolean isInsuranceEnabled;
    @NotNull
    private Boolean isSalaryClient;
    private CreditStatus creditStatus;
}
