package com.nest.epargne.repositories;
import com.nest.epargne.entities.Debit;
import com.nest.epargne.entities.Utilisateur;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface debitRepository extends PagingAndSortingRepository<Debit, UUID> {
}
