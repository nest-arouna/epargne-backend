package com.nest.epargne.services;
import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.entities.Reponse;

import java.util.UUID;

public interface ICreditService {
      // SERVICE CREDIT

	  public Reponse updateCredit(CreditDtoRequest creditDtoRequest);
	public Reponse addCredit(CreditDtoRequest creditDtoRequest);

	public Reponse getCreditById(UUID id);
	public Reponse getAllCreditsByAdminCaisse(Long date_operation, Double montant, UUID patientID, String identifiant_credit, int size, int page);

	public Reponse getAllCredits(Long date_operation, Double montant, UUID patientID, String identifiant_credit, int size, int page);
	public Reponse succesCredit(UUID creditID);
	public Reponse failedCredit(UUID creditID);


	 }
