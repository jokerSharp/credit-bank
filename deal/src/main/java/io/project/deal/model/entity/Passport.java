package io.project.deal.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passport")
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "passport_id")
    private UUID passportId;
    @NotBlank
    @Column(name = "series")
    private String series;
    @NotBlank
    @Column(name = "number")
    private String number;
    @Column(name = "issue_branch")
    private String issueBranch;
    @Column(name = "issue_date")
    private LocalDate issueDate;
}
