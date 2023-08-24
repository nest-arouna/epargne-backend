package com.nest.epargne.repositories;
import java.util.List;
import java.util.UUID;

import com.nest.epargne.entities.Debit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDaoDebit extends JpaRepository<Debit, UUID> {
    List<Debit> findByPatientID(UUID id);
    List<Debit> findByProfessionnalID(UUID id);
    List<Debit> findByPatientIDAndProfessionnalID(UUID patientID,UUID professionnalID);


}
