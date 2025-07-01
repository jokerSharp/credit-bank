package io.project.deal.model.entity;

import io.project.deal.model.enums.EmploymentStatus;
import io.project.deal.model.enums.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employment")
@Entity
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employment_id")
    private UUID employmentId;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmploymentStatus status;
    @NotBlank
    @Column(name = "employer_inn")
    private String employerInn;
    @NotNull
    @Column(name = "salary")
    private BigDecimal salary;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;
    @NotNull
    @Column(name = "work_experience_total")
    private Integer workExperienceTotal;
    @NotNull
    @Column(name = "work_experience_current")
    private Integer workExperienceCurrent;
}
