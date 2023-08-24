package com.nest.epargne.rest;
import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.services.ICreditService;
import com.nest.epargne.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
public class CreditRestControler {
	@Autowired
	private ICreditService creditService;

	@PostMapping(Utility.ADD_CREDIT)
	public Reponse getAddCredit(@RequestBody CreditDtoRequest credit){
		Reponse resultatCreation = creditService.addCredit(credit);
		return resultatCreation;
    }

	@GetMapping(Utility.GET_CREDIT_BY_ID)
	public Reponse getUserById(@PathVariable(value = "id") UUID creditID){
		Reponse	userUpdate =creditService.getCreditById(creditID);
		return userUpdate ;
    }
	@GetMapping(Utility.ADD_CREDIT)
	public Reponse getCredit(
			@RequestParam(required = false) UUID patientID,
			@RequestParam(required = false)  Long date_operation,
			@RequestParam(required = false) Double montant,
			@RequestParam(required = false) String identifiant_credit,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size
	)
	{

		Reponse	credits =creditService.getAllCreditsByAdminCaisse(date_operation,montant,patientID,identifiant_credit,size,page);

		return credits ;
	}

	@GetMapping(Utility.SUCCESS_CREDIT)
	public Reponse succesCredit(@PathVariable(value = "id") UUID creditID){
		Reponse	userUpdate =creditService.succesCredit(creditID);
		return userUpdate ;
	}
	@GetMapping(Utility.FAILED_CREDIT)
	public Reponse failedCredit(@PathVariable(value = "id") UUID creditID){
		Reponse	userUpdate =creditService.failedCredit(creditID);
		return userUpdate ;
	}
	@GetMapping(Utility.GET_ALL_CREDIT)
	public Reponse getCredits(
			@PathVariable(value = "id") UUID creditID,
			@RequestParam(required = false)  Long date_operation,
			@RequestParam(required = false) Double montant,
			@RequestParam(required = false) String identifiant_credit,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size
	)
	{

		Reponse	credits =creditService.getAllCredits(date_operation,montant,creditID,identifiant_credit,size,page);

		return credits ;
	}
	      
}
