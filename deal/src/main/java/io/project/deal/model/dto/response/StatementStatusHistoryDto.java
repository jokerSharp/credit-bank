package io.project.deal.model.dto.response;

import io.project.deal.model.enums.ApplicationStatus;
import io.project.deal.model.enums.ChangeType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class StatementStatusHistoryDto {

    ApplicationStatus status;
    LocalDateTime time;
    ChangeType changeType;
}
