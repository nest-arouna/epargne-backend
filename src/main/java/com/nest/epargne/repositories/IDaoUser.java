package com.nest.epargne.repositories;
import com.nest.epargne.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDaoUser extends PagingAndSortingRepository<Utilisateur, UUID> {
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByPhone(String phone);
    Optional<Utilisateur> findByToken(String token);


}
