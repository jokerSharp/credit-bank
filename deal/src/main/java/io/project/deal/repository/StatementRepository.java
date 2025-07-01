package io.project.deal.repository;

import io.project.deal.model.entity.Credit;
import io.project.deal.model.entity.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatementRepository extends CrudRepository<Statement, UUID> {
    Optional<Statement> findByCredit(Credit credit);
}
