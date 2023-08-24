package com.nest.epargne.services;
import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.entities.Reponse;

import java.util.UUID;

public interface ICompteService {
      // SERVICE COMPTE
	public Reponse etatCompte(UUID id);


	 }
