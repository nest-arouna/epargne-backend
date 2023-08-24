package com.nest.epargne.repositories;

import com.nest.epargne.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDaoCredit extends JpaRepository<Credit, UUID> {
    List<Credit> findByPatientIDOrderByDateOperationDesc(UUID id);
    List<Credit> findByOrderByDateOperationDesc();

}
