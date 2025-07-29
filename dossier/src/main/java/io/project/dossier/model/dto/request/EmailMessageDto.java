package io.project.dossier.model.dto.request;

import io.project.dossier.model.enums.EmailMessageTheme;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Jacksonized
@Builder
@Value
public class EmailMessageDto {

    String address;
    EmailMessageTheme theme;
    UUID statementId;
    @NotNull
    String text;
}
