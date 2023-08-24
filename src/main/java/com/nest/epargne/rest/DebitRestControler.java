package com.nest.epargne.rest;

import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.dto.request.DebitDtoRequest;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.services.ICreditService;
import com.nest.epargne.services.IDebitService;
import com.nest.epargne.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class DebitRestControler {
	@Autowired
	private IDebitService debitService;

	@PostMapping(Utility.DEBITS)
	public Reponse getAddDebit(@RequestBody DebitDtoRequest credit){
		Reponse resultatCreation = debitService.addDebit(credit);
		return resultatCreation;
    }


	
	@GetMapping(Utility.GET_DEBIT_BY_ID)
	public Reponse getDebitById(@PathVariable(value = "id") UUID creditID){
		Reponse	userUpdate =debitService.getDebitById(creditID);
		return userUpdate ;
    }


	@GetMapping(Utility.DEBITS)
	public Reponse getDebits(
			@RequestParam(required = false)  Long date_operation,
			@RequestParam(required = false) Double montant,
			@RequestParam(required = false) String motif,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size
	)
	{

		Reponse	credits =debitService.getAllDebits(date_operation,montant,motif,size,page);

		return credits ;
	}
	@GetMapping(Utility.GET_ALL_DEBIT_BY_PATIENTS)
	public Reponse getDebitByPatients(
			@PathVariable(value = "id") UUID patientID,
			@RequestParam(required = false)  Long date_operation,
			@RequestParam(required = false) Double montant,
			@RequestParam(required = false) String motif,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size
	)
	{

		Reponse	credits =debitService.getAllDebitsByPatients(date_operation,montant,patientID,motif,size,page);

		return credits ;
	}

	@GetMapping(Utility.GET_ALL_DEBIT_BY_PROFESSIONNALS)
	public Reponse getDebitByProfessionnals(
			@PathVariable(value = "id") UUID professionnalID,
			@RequestParam(required = false)  Long date_operation,
			@RequestParam(required = false) Double montant,
			@RequestParam(required = false) String motif,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size
	)
	{

		Reponse	credits =debitService.getAllDebitsByProfessionnels(date_operation,montant,professionnalID,motif,size,page);

		return credits ;
	}

	@GetMapping(Utility.GET_ALL_DEBIT_FOR_PATIENT_BY_PROFESSIONNALS)
	public Reponse getDebitForPaatientByProfessionnals(
			@PathVariable(value = "professionnalID") UUID professionnalID,
			@PathVariable(value = "patientID") UUID patientID,
			@RequestParam(required = false)  Long date_operation,
			@RequestParam(required = false) Double montant,
			@RequestParam(required = false) String motif,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size
	)
	{
		Reponse	credits =debitService.getAllDebitsForPatientByProfessionnels(date_operation, montant,patientID,professionnalID,motif,size,page);
		return credits ;
	}
	      
}
