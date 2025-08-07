package io.project.apigateway.model.dto.request;

import io.project.apigateway.model.dto.enums.EmailMessageTheme;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Schema(example = """
        {
            "text": "012345"
        }
        """)
@Builder
@Value
public class EmailMessageDto {

    String address;
    EmailMessageTheme theme;
    UUID statementId;
    String text;
}
