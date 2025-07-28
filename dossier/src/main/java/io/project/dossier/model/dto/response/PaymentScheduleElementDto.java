package io.project.dossier.model.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Jacksonized
@Builder
@Value
public class PaymentScheduleElementDto {
    @NotNull
    Integer number;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    LocalDate date;
    @NotNull
    BigDecimal totalPayment;
    @NotNull
    BigDecimal interestPayment;
    @NotNull
    BigDecimal debtPayment;
    @NotNull
    BigDecimal remainingDebt;
}
