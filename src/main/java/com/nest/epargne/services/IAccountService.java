package com.nest.epargne.services;
import com.nest.epargne.dto.request.UserDtoRequest;
import com.nest.epargne.entities.ChangePasswordDtoRequest;
import com.nest.epargne.entities.Login;
import com.nest.epargne.entities.Reponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IAccountService {
      // SERVICE UTILISATEUR
	  public void initAccount();
	  public String getToken(String email , String password);
	  public Reponse updateUser(UserDtoRequest professionnalDtoRequest);
	  public Reponse getUserById(UUID id);
	  public Reponse getAllProfesionnals( String lastname, String firstname, String adress, String email,String typeOfUser,String phone,Long birthDay,
							   String role,
							   String identification_code,
							   int size,
							   int page);
	public Reponse getAllPatients( String assurance,String lastname, String firstname, String adress, String email,String typeOfUser,String phone,Long birthDay,
										String role,
										String identification_code,
										int size,
										int page);
	  public Reponse getUserByEmail(String Email);
	  public Reponse login_in(Login login);
	  public Reponse login_up(UserDtoRequest User);
	public Reponse login_up_patient(UserDtoRequest User);
	public Reponse login_up_patient_with_file(MultipartFile file, UserDtoRequest User);
	public Reponse update_patient_with_file(MultipartFile file, UserDtoRequest User);

	public Reponse changePassword(ChangePasswordDtoRequest changePasswordDtoRequest);

	  public UUID getIdUserByToken(String token);
	public Reponse lockUser(UUID id);

}
