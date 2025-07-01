package io.project.deal.model.entity;

import io.project.deal.model.dto.response.StatementStatusHistoryDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statement")
@Entity
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statement_id")
    private UUID statementId;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;
    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "applied_offer")
    private LoanOfferDto appliedOffer;
    @Column(name = "sign_date")
    private LocalDateTime signDate;
    @Column(name = "ses_code")
    private String sesCode;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "status_history")
    private List<StatementStatusHistoryDto> statusHistory;
}
