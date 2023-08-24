package com.nest.epargne.rest;
import com.nest.epargne.dto.request.UserDtoRequest;
import com.nest.epargne.entities.ChangePasswordDtoRequest;
import com.nest.epargne.entities.Login;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.services.IAccountService;
import com.nest.epargne.services.ICompteService;
import com.nest.epargne.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountRestControler {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ICompteService compteService;

	@PostMapping(Utility.ADD_USER)
	public Reponse getAddUser(@RequestBody UserDtoRequest user){
		Reponse resultatCreation = accountService.login_up(user);
		return resultatCreation;
    }
	@PostMapping(path = Utility.ADD_PATIENT)
	public Reponse addPatient(@RequestBody UserDtoRequest user){
		Reponse resultatCreation = accountService.login_up_patient(user);
		return resultatCreation;
	}


	@PostMapping(path = Utility.ADD_PATIENT_WITH_FILE, headers = "Content-Type= multipart/form-data")
	public Reponse addPatientFile(
			@RequestParam(value = "file",required = false) MultipartFile file,
			@RequestParam(value = "lastname",required = false) String lastname,
			@RequestParam(value = "firstname",required = false) String firstname,
			@RequestParam(value = "assurance",required = false) String assurance,
			@RequestParam(value = "adress",required = false) String adress,
			@RequestParam(value = "email",required = true) String email,
			@RequestParam(value = "phone",required = true) String phone,
			@RequestParam(value = "birthDay",required = true) String birthDay,
			@RequestParam(value = "identification_code",required = false) String identification_code

	){
		UserDtoRequest user = new UserDtoRequest();
		user.setLastname(lastname);
		user.setAssurance(assurance);
		user.setFirstname(firstname);
		user.setAdress(adress);
		user.setEmail(email);
		user.setPhone(phone);
		user.setBirthDay(Long.valueOf(birthDay));
		user.setIdentification_code(identification_code);
		Reponse resultatCreation = accountService.login_up_patient_with_file(file,user);
		return resultatCreation;
	}
	@PostMapping(path = Utility.UPDATE_PATIENT_WITH_FILE)
	public Reponse updatePatientFile(
			@RequestParam(value = "file",required = false) MultipartFile file,
			@RequestParam(value = "id",required = false) UUID id,
			@RequestParam(value = "lastname",required = false) String lastname,
			@RequestParam(value = "firstname",required = false) String firstname,
			@RequestParam(value = "assurance",required = false) String assurance,
			@RequestParam(value = "adress",required = false) String adress,
			@RequestParam(value = "email",required = true) String email,
			@RequestParam(value = "phone",required = true) String phone,
			@RequestParam(value = "birthDay",required = true) Long birthDay,
			@RequestParam(value = "identification_code",required = false) String identification_code

	){


		UserDtoRequest user = new UserDtoRequest();
		user.setLastname(lastname);
		user.setID(id);
		user.setFirstname(firstname);
		user.setAdress(adress);
		user.setEmail(email);
		user.setAssurance(assurance);
		user.setPhone(phone);
		user.setBirthDay(birthDay);
		user.setIdentification_code(identification_code);

		Reponse resultatCreation = accountService.update_patient_with_file(file,user);
		return resultatCreation;
	}

	@PostMapping(Utility.DO_LOGIN)
	public Reponse Login(@RequestBody Login login){

		Reponse resultatCreation = accountService.login_in(login);

		return resultatCreation;
	}
	@PostMapping(Utility.UPDATE_PASSWORD)
	public Reponse updatePwd(@PathVariable(value = "id") UUID userID ,@RequestBody ChangePasswordDtoRequest changePasswordDtoRequest){
		changePasswordDtoRequest.setID(userID);
		Reponse resultatCreation = accountService.changePassword(changePasswordDtoRequest);

		return resultatCreation;
	}
	@PostMapping(Utility.UPDATE_USER)
	public Reponse updateUser( @RequestBody UserDtoRequest user){
		Reponse updateDroit = accountService.updateUser(user);
		return updateDroit;
    }

	@GetMapping(Utility.COMPTE)
	public Reponse getCompteById(@PathVariable(value = "id") UUID patientID){
		Reponse	userUpdate =compteService.etatCompte(patientID);
		return userUpdate ;
	}
	@GetMapping(Utility.GET_USER_BY_ID)
	public Reponse getUserById(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =accountService.getUserById(droitID);
		return userUpdate ;
    }
	@DeleteMapping(Utility.DELETE_USER_BY_ID)
	public Reponse lockUser(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =accountService.lockUser(droitID);
		return userUpdate ;
	}

	@GetMapping(Utility.GET_ALL_PROFESSIONNALS)
	public Reponse getUsers(
			@RequestParam(required = false) String lastname,
			@RequestParam(required = false) String firstname,
			@RequestParam(required = false) String adress,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String typeOfUser,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) Long birthDay,
			@RequestParam(required = false) String role,
			@RequestParam(required = false) String identification_code,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size

	)
	{
		Reponse	users =accountService.getAllProfesionnals( lastname,firstname,adress,email,
				typeOfUser,phone,birthDay, role, identification_code,size,page);


		return users ;
	}

	@GetMapping(Utility.GET_ALL_PATIENTS)
	public Reponse getPatients(
			@RequestParam(required = false) String lastname,
			@RequestParam(required = false) String firstname,
			@RequestParam(required = false) String adress,
			@RequestParam(required = false) String assurance,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String typeOfUser,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) Long birthDay,
			@RequestParam(required = false) String role,
			@RequestParam(required = false) String identification_code,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size

	)
	{

		Reponse	users =accountService.getAllPatients(assurance, lastname,firstname,adress,email,
				typeOfUser,phone,birthDay, role, identification_code,size,page);


		return users ;
	}
	      
}
