package com.nest.epargne.repositories;
import com.nest.epargne.entities.Debit;
import com.nest.epargne.entities.IntouchCallBack;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IntouchDaoRepository extends PagingAndSortingRepository<IntouchCallBack, UUID> {
}
