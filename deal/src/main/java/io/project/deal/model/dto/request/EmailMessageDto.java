package io.project.deal.model.dto.request;

import io.project.deal.model.enums.EmailMessageTheme;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class EmailMessageDto {

    String address;
    EmailMessageTheme theme;
    UUID statementId;
    @NotNull
    String text;
}
