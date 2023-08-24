package com.nest.epargne.services;
import com.nest.epargne.dto.request.DebitDtoRequest;
import com.nest.epargne.entities.Reponse;

import java.util.UUID;

public interface IDebitService {
      // SERVICE DEBIT

	  public Reponse updateDebit(DebitDtoRequest debitDtoRequest);
	public Reponse addDebit(DebitDtoRequest debitDtoRequest);

	public Reponse getDebitById(UUID id);
	  public Reponse getAllDebitsByPatients(Long date_operation, Double montant, UUID patientID, String motif, int size, int page);
	public Reponse getAllDebitsByProfessionnels(Long date_operation, Double montant, UUID professionnalID, String motif, int size, int page);
	public Reponse getAllDebitsForPatientByProfessionnels(Long date_operation, Double montant,UUID patientID, UUID professionnalID, String motif, int size, int page);
	public Reponse getAllDebits(Long date_operation, Double montant,String motif, int size, int page);


	 }
