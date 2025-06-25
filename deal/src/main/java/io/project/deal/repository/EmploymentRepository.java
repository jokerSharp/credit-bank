package io.project.deal.repository;

import io.project.deal.model.entity.Employment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmploymentRepository extends CrudRepository<Employment, UUID> {
}
