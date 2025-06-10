package io.project.calculator.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(example = """
        {
            "amount": "123000",
            "term": "10",
            "firstName": "John",
            "lastName": "Doe",
            "email": "john@mail.com",
            "birthdate": "2000-10-10",
            "passportSeries": "1234",
            "passportNumber": "567890"
        }
        """)
@Builder
@Value
public class LoanStatementRequestDto {
    @NotNull
    @Min(20000)
    BigDecimal amount;
    @NotNull
    @Min(6)
    Integer term;
    @NotNull
    @Size(min = 2, max = 30)
    String firstName;
    @NotNull
    @Size(min = 2, max = 30)
    String lastName;
    @Size(min = 2, max = 30)
    String middleName;
    @NotNull
    @Pattern(regexp = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$")
    String email;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthdate;
    @NotNull
    @Pattern(regexp = "^\\d{4}$")
    String passportSeries;
    @NotNull
    @Pattern(regexp = "^\\d{6}$")
    String passportNumber;
}
