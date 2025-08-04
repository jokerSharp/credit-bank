package io.project.apigateway.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

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
    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    String email;
    LocalDate birthdate;
    String passportSeries;
    String passportNumber;
}
