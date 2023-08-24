package com.nest.epargne.repositories;

import com.nest.epargne.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IDaoCompte extends JpaRepository<Compte, UUID> {
    Optional<Compte> findByPatientID(UUID id);

}
