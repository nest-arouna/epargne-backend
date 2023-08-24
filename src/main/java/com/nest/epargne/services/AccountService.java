package com.nest.epargne.services;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.nest.epargne.EpargneApplication;
import com.nest.epargne.dto.request.UserDtoRequest;
import com.nest.epargne.dto.response.UserDtoResponse;
import com.nest.epargne.entities.*;
import com.nest.epargne.repositories.IDaoCompte;
import com.nest.epargne.repositories.IDaoUser;
import com.nest.epargne.security.JwtTokenUtil;
import com.nest.epargne.utils.Utility;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AccountService implements IAccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private IDaoCompte compteRepository;
	@Autowired
	private IFileService fileService;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private IDaoUser userRepository;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired(required=true)
	private ModelMapper modelMapper;

	@Override
	public Reponse login_up(UserDtoRequest user)
	{

		Reponse reponse = new Reponse();
		try
		{

			if(user.getPhone() != null && user.getPhone().length() > 0 && user.getEmail() != null && user.getEmail().length() > 0)
			{

				Optional<Utilisateur> phoneUser =userRepository.findByPhone(user.getPhone());
				Optional<Utilisateur>  emailUser =userRepository.findByEmail(user.getEmail());

				if(phoneUser.isPresent())
				{
					reponse.setMessage("Ce numéro de téléphone est déjà utilisé svp !");
					reponse.setCode(201);
				}
				else if(emailUser.isPresent())
				{
					reponse.setMessage("Cet email est déjà utilisé svp !");
					reponse.setCode(201);
				}
				else {
					user.setStatus(true);
					user.setDate_enregistrement(new Date().getTime());
					user.setTypeOfUser(Utility.TYPE_PROFESSIONNAL);
					Utilisateur userConverted =modelMapper.map(user, Utilisateur.class);
					userConverted.setPassword(bCryptPasswordEncoder.encode(user.getEmail()));
					Utilisateur userSave = userRepository.save(userConverted);
					reponse.setData(modelMapper.map(userSave, UserDtoResponse.class));
					reponse.setMessage("Ce compte a été enregistré avec succès");
					reponse.setCode(200);
				}

			}
			else
			{

				reponse.setMessage("Veuillez renseigner l'email et le téléphone svp !");
				reponse.setCode(201);

			}


		}
		catch (Exception e)
		{
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setCode(500);
			reponse.setMessage("Un problème de serveur  !");
		}  

		return reponse ;


	}

	@Override
	public Reponse login_up_patient(UserDtoRequest user)
	{


		Reponse reponse = new Reponse();
		try
		{

			if(user.getPhone() != null && user.getPhone().length() > 0 && user.getEmail() != null && user.getEmail().length() > 0)
			{

				Optional<Utilisateur> phoneUser =userRepository.findByPhone(user.getPhone());
				Optional<Utilisateur>  emailUser =userRepository.findByEmail(user.getEmail());

				if(phoneUser.isPresent())
				{
					reponse.setMessage("Ce numéro de téléphone est déjà utilisé  !");
					reponse.setCode(201);
				}
				else if(emailUser.isPresent())
				{
					reponse.setMessage("Cet email est déjà utilisé  !");
					reponse.setCode(201);
				}
				else {
					user.setStatus(true);
					user.setDate_enregistrement(new Date().getTime());
					user.setTypeOfUser(Utility.TYPE_PATIENT);
					Utilisateur userConverted =modelMapper.map(user, Utilisateur.class);
					userConverted.setPassword(bCryptPasswordEncoder.encode(user.getEmail()));
					Utilisateur userSave = userRepository.save(userConverted);
					Compte compte= new Compte();
					compte.setPatientID(userSave.getID());
					compteRepository.save(compte);
					reponse.setMessage("Ce compte a été enregistré avec succès ");
					reponse.setData(modelMapper.map(userSave, UserDtoResponse.class));
					reponse.setCode(200);
				}

			}
			else
			{

				reponse.setMessage("Veuillez renseigner l'email et le téléphone svp !");
				reponse.setCode(201);

			}


		}
		catch (Exception e)
		{
			reponse.setCode(500);
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setMessage("Un problème de serveur  !");
		}

		return reponse ;


	}

	@Override
	public Reponse login_up_patient_with_file(MultipartFile file, UserDtoRequest user) {
		Reponse reponse = new Reponse();
		try
		{
			if(user.getPhone() != null && user.getPhone().length() > 0 && user.getEmail() != null && user.getEmail().length() > 0)
			{

				Optional<Utilisateur> phoneUser =userRepository.findByPhone(user.getPhone());
				Optional<Utilisateur>  emailUser =userRepository.findByEmail(user.getEmail());

				if(phoneUser.isPresent())
				{
					reponse.setMessage("Ce numéro de téléphone est déjà utilisé  !");
					reponse.setCode(201);
				}
				else if(emailUser.isPresent())
				{
					reponse.setMessage("Cet email est déjà utilisé  !");
					reponse.setCode(201);
				}
				else {
					user.setStatus(true);
					user.setDate_enregistrement(new Date().getTime());
					user.setTypeOfUser(Utility.TYPE_PATIENT);
					Utilisateur userConverted =modelMapper.map(user, Utilisateur.class);
					userConverted.setPassword(bCryptPasswordEncoder.encode(user.getEmail()));
					Utilisateur userSave = userRepository.save(userConverted);
					Compte compte= new Compte();
					compte.setPatientID(userSave.getID());
					compteRepository.save(compte);
					//	Reponse respon=this.fileService.uploadFile(file);
			/*	if(respon.getCode() == 200)
				{
					userSave.setUrl_supported((String) respon.getData());
					userSave = userRepository.save(userSave);
					reponse.setMessage("Ce compte avec la prise en charge ont été enregistré avec succès");
				}
				else
				{
					reponse.setMessage("Ce compte a été enregistré avec succès sans la prise en charge");
				} */
					reponse.setMessage("Ce compte a été enregistré avec succès ");

					reponse.setData(modelMapper.map(userSave, UserDtoResponse.class));
					reponse.setCode(200);
				}

			}
			else
			{

				reponse.setMessage("Veuillez renseigner l'email et le téléphone svp !");
				reponse.setCode(201);

			}



		}
		catch (Exception e)
		{
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setCode(500);
			reponse.setMessage("Un problème de serveur  !");
		}

		return reponse ;

	}

	@Override
	public Reponse update_patient_with_file(MultipartFile file, UserDtoRequest user) {
		Reponse reponse = new Reponse();
		try
		{
			if(user.getPhone() != null && user.getPhone().length() > 0 && user.getEmail() != null && user.getEmail().length() > 0)
			{

				Optional<Utilisateur> phoneUser =userRepository.findByPhone(user.getPhone());
				Optional<Utilisateur>  emailUser =userRepository.findByEmail(user.getEmail());

				if(phoneUser.isPresent() && phoneUser.get().getID().compareTo(user.getID()) != 0)
				{
					reponse.setMessage("Ce numéro de téléphone est déjà utilisé svp !");
					reponse.setCode(201);
				}
				else if(emailUser.isPresent() && emailUser.get().getID().compareTo(user.getID()) != 0)
				{
					reponse.setMessage("Cet email est déjà utilisé svp !");
					reponse.setCode(201);
				}
				else {
					Optional<Utilisateur> userUpdate=userRepository.findById(user.getID());

					if(userUpdate.isPresent())
					{
						if(user.getAssurance() != null){userUpdate.get().setAssurance(user.getAssurance());}
						if(user.getLastname() != null){userUpdate.get().setLastname(user.getLastname());}
						if(user.getFirstname() != null){userUpdate.get().setFirstname(user.getFirstname());}
						if(user.getAdress() != null){userUpdate.get().setAdress(user.getAdress());}
						if(user.getEmail() != null){userUpdate.get().setEmail(user.getEmail());}
						if(user.getPhone() != null){userUpdate.get().setPhone(user.getPhone());}
						if(user.getBirthDay() != null ){userUpdate.get().setBirthDay(user.getBirthDay());}
						if(user.getIdentification_code() != null){userUpdate.get().setIdentification_code(user.getIdentification_code());}
						//	this.fileService.deletedFile(userUpdate.get().getUrl_supported());
						Utilisateur userSave = userRepository.save(userUpdate.get());

						//	Reponse respon=this.fileService.uploadFile(file);
				/*	if(respon.getCode() == 200)
					{
						userSave.setUrl_supported((String) respon.getData());
						userSave = userRepository.save(userSave);
						reponse.setMessage("Ce compte avec la prise en charge ont été modifié avec succès");
					}
					else
					{
						reponse.setMessage("Ce compte a été enregistré avec succès sans la prise en charge");
					} */
						reponse.setMessage("Ce compte a été modifié avec succès ");
						reponse.setData(modelMapper.map(userSave, UserDtoResponse.class));
						reponse.setCode(200);

					}
					else
					{
						reponse.setMessage("Ce compte n'existe plus");
					}
				}

			}
			else
			{

				reponse.setMessage("Veuillez renseigner l'email et le téléphone svp !");
				reponse.setCode(201);

			}



		}
		catch (Exception e)
		{
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setCode(500);
			reponse.setMessage("Un problème de serveur  !"+e.getMessage());
		}

		return reponse ;
	}

	@Override
	public Reponse login_in(Login login)
	{

		Reponse response = new Reponse();
		try
		{
			Optional<Utilisateur> ifUserExist=userRepository.findByPhone(login.getPhone());


			if(ifUserExist.isPresent())
			{

				Utilisateur userC=ifUserExist.get();
				if((bCryptPasswordEncoder.matches(login.getPassword(), userC.getPassword())))
				{
					HashMap<String, String> credentials = new HashMap<String, String>();
					String token=this.getToken(login.getPhone(), login.getPassword());
					userC.setToken(token);
					userRepository.save(userC);
					credentials.put("id", userC.getID().toString());
					credentials.put("token", token);
					credentials.put("role", userC.getRole());
					credentials.put("type", userC.getTypeOfUser());
					credentials.put("email", userC.getEmail());
					credentials.put("lastname", userC.getLastname());
					credentials.put("firstname", userC.getFirstname());
					credentials.put("phone", userC.getPhone());
					response.setCode(200);
					response.setMessage("La connexion a reussi !");
					response.setData(credentials);

				}
				else
				{
					response.setCode(201);
					response.setMessage("Mot de passe incorrect !");
				}
			}
			else
			{
				response.setCode(201);
				response.setMessage("Cet compte n'existe pas !");

			}


		}
		catch (Exception e)
		{
			logger.error(" une exception est survenue "+e.getMessage());
			response.setCode(500);
			response.setMessage("Un problème de serveur !");
		}    


		return response ;
	}

	@Override
	public String getToken(String phone , String password)
	{
		try {
			authenticate(phone,  password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return token;

	}



	public  void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	@Override
	public void initAccount() {

		UserDtoRequest userDtoRequest = new UserDtoRequest();
		userDtoRequest.setRole(RoleEmun.ADMIN.label);
		userDtoRequest.setTypeOfUser("PROFESSIONNAL");
		userDtoRequest.setEmail("arouna.sanou@nest.sn");
		userDtoRequest.setPhone("775073511");
		userDtoRequest.setFirstname("NEST-ADMIN-FIRSTNAME");
		userDtoRequest.setLastname("NEST-ADMIN-LASTNAME");
		Reponse reponse=	login_up(userDtoRequest);
		logger.info(" Le compte par defaut a été crée avec un code : "+reponse.getCode() + " :"+reponse.getMessage());
	}

	@Override
	public Reponse updateUser(UserDtoRequest user) {
		Reponse reponse = new Reponse();
		try
		{
			if(user.getPhone() != null && user.getPhone().length() > 0 && user.getEmail() != null && user.getEmail().length() > 0)
			{

				Optional<Utilisateur> phoneUser =userRepository.findByPhone(user.getPhone());
				Optional<Utilisateur>  emailUser =userRepository.findByEmail(user.getEmail());

				if(phoneUser.isPresent() && phoneUser.get().getID().compareTo(user.getID()) != 0)
				{
					reponse.setMessage("Ce numéro de téléphone est déjà utilisé svp !");
					reponse.setCode(201);
				}
				else if(emailUser.isPresent() && emailUser.get().getID().compareTo(user.getID()) != 0)
				{
					reponse.setMessage("Cet email est déjà utilisé svp !");
					reponse.setCode(201);
				}
				else {
					Optional<Utilisateur>  userById = userRepository.findById(user.getID());

					if(user.getLastname() != null && !user.getLastname().equals(userById.get().getLastname()))
					{
						userById.get().setLastname(user.getLastname());
					}
					if(user.getFirstname() != null && !user.getFirstname().equals(userById.get().getFirstname()))
					{
						userById.get().setFirstname(user.getFirstname());
					}
					if(user.getEmail() != null && !user.getEmail().equals(userById.get().getEmail()))
					{
						userById.get().setEmail(user.getEmail());
					}
					if(user.getPhone() != null && !user.getPhone().equals(userById.get().getPhone()))
					{
						userById.get().setPhone(user.getPhone());
					}

					if(user.getAdress() != null && !user.getAdress().equals(userById.get().getAdress()))
					{
						userById.get().setAdress(user.getAdress());
					}

					if(user.getTypeOfUser() != null && !user.getTypeOfUser().equals(userById.get().getTypeOfUser()))
					{
						userById.get().setTypeOfUser(user.getTypeOfUser());
					}
					if(user.getBirthDay() != null && user.getBirthDay() != userById.get().getBirthDay())
					{
						userById.get().setBirthDay(user.getBirthDay());
					}
					if(user.getRole() != null && !user.getRole().equals(userById.get().getRole()))
					{
						userById.get().setRole(user.getRole());
					}
					if(user.getIdentification_code() != null && !user.getIdentification_code().equals(userById.get().getIdentification_code()))
					{
						userById.get().setIdentification_code(user.getIdentification_code());
					}

					Utilisateur userSave = userRepository.save(userById.get());
					UserDtoResponse userDtoResponse=modelMapper.map(userSave, UserDtoResponse.class);
					reponse.setData(userDtoResponse);
					reponse.setMessage("Ce compte a été modifié avec succès");
					reponse.setCode(200);

				}

			}
			else
			{

				reponse.setMessage("Veuillez renseigner l'email et le téléphone svp !");
				reponse.setCode(201);

			}


		}
		catch (Exception e)
		{
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setCode(500);
			reponse.setMessage("Une erreur interne est survenue coté serveur  !");	
		}  

		return reponse ;
	}
	@Override
	public Reponse getUserById(UUID id) {
		Reponse reponse = new Reponse();
		try
		{
			Optional<Utilisateur> user = userRepository.findById(id);


			if(user.isPresent())
			{
				UserDtoResponse userConverted =modelMapper.map(user.get(), UserDtoResponse.class);
				//userConverted.setUrl_supported(this.fileService.getPathFile(user.get().getUrl_supported()));
				reponse.setData(userConverted);
				reponse.setMessage("Ce compte a été retrouvé avec succès");
				reponse.setCode(200);

			}		
			else
			{
				reponse.setMessage("Ce compte n'existe pas");
				reponse.setCode(201);

			}


		}
		catch (Exception e) {
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setMessage("Une erreur interne est survenue");
			reponse.setCode(500);

		} 

		return reponse;
	}

	@Override
	public Reponse getAllProfesionnals(String lastname, String firstname, String adress, String email,String typeOfUser,String phone,Long birthDay,
							String role,
							String identification_code,
							int size,
							int page)
	 {




		 int skipCount = (page - 1) * size;
		Reponse reponse = new Reponse();
		try
		{

			  HashMap<String, Object> datas  = new HashMap<String, Object>();


				String queryInit = "SELECT c FROM Utilisateur c WHERE c.typeOfUser = 'PROFESSIONNAL' ";

			if(lastname != null && lastname.length() != 0 )
			{
				queryInit +="AND c.lastname LIKE '" +lastname +"%'";
			}
			if(firstname != null && firstname.length() != 0)
			{
				queryInit +="AND c.firstname LIKE '" +firstname +"%'";
			}
			if(adress != null && adress.length() != 0)
			{
				queryInit +="AND c.adress LIKE '" +adress +"%'";
			}
			if(email != null && email.length() != 0)
			{
				queryInit +="AND c.email LIKE '" +email +"%'";
			}
			if(typeOfUser != null && typeOfUser.length() != 0)
			{
				queryInit +="AND c.typeOfUser LIKE '" +typeOfUser +"%'";
			}
			if(phone != null && phone.length() != 0)
			{
				queryInit +="AND c.phone ='" +phone +"'";
			}
			if(birthDay != null && birthDay != 0)
			{
				queryInit +="AND c.birthDay ='" +birthDay +"'";
			}
			if(role != null && role.length() != 0)
			{
				queryInit +="AND c.role LIKE '" +role +"%'";
			}
			if(identification_code != null && identification_code.length() != 0)
			{
				queryInit +="AND c.identification_code LIKE '" +identification_code +"%'";
			}
			queryInit +="ORDER BY c.date_enregistrement DESC";



			TypedQuery<Utilisateur> query = entityManager.createQuery(queryInit, Utilisateur.class);


			List<UserDtoResponse> users = query.getResultList()
					.stream()
					.skip(skipCount)
					.limit(size)
					.map(p->modelMapper.map(p, UserDtoResponse.class) )
					.collect(Collectors.toList());

			datas.put("totalUsers",query.getResultList().size());
			datas.put("data", users);

			reponse.setData(datas);
			reponse.setMessage("Liste des comptes");
			reponse.setCode(200);


		}
		catch (Exception e) {
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setMessage("Une erreur interne est survenue");
			reponse.setCode(500);

		}

		return reponse;



	}

	@Override
	public Reponse getAllPatients(String assurance,String lastname, String firstname, String adress, String email, String typeOfUser, String phone, Long birthDay, String role, String identification_code, int size, int page) {
		int skipCount = (page - 1) * size;
		Reponse reponse = new Reponse();
		try
		{

			HashMap<String, Object> datas  = new HashMap<String, Object>();

			String queryInit = "SELECT c FROM Utilisateur c WHERE c.typeOfUser = 'PATIENT'";

			if(lastname != null && lastname.length() != 0 )
			{
				queryInit +="AND  c.lastname LIKE '" +lastname +"%'";
			}
			if(firstname != null && firstname.length() != 0)
			{
				queryInit +="AND c.firstname LIKE '" +firstname +"%'";
			}
			if(adress != null && adress.length() != 0)
			{
				queryInit +="AND c.adress LIKE '" +adress +"%'";
			}
			if(assurance != null && assurance.length() != 0)
			{
				queryInit +="AND c.assurance LIKE '" +assurance +"%'";
			}
			if(email != null && email.length() != 0)
			{
				queryInit +="AND c.email LIKE '" +email +"%'";
			}

			if(phone != null && phone.length() != 0)
			{
				queryInit +="AND c.phone ='" +phone +"%'";
			}
			if(birthDay != null && birthDay != 0)
			{
				queryInit +="AND c.birthDay ='" +birthDay +"'";
			}

			if(identification_code != null && identification_code.length() != 0)
			{
				queryInit +="AND c.identification_code LIKE '" +identification_code +"%'";
			}


			queryInit +="ORDER BY c.date_enregistrement DESC";

			TypedQuery<Utilisateur> query = entityManager.createQuery(queryInit, Utilisateur.class);


			List<UserDtoResponse> users = query.getResultList()
					.stream()
					.skip(skipCount)
					.limit(size)
					.map(p->modelMapper.map(p, UserDtoResponse.class) )
					.map( g ->{
						Optional<Compte> compte= compteRepository.findByPatientID(g.getID());
						if(compte.isPresent())
						{
							g.setSolde(compte.get().getMontant_courant());
						}
						return g;

					})
					.collect(Collectors.toList());

			datas.put("totalUsers",query.getResultList().size());
			datas.put("data", users);

			reponse.setData(datas);
			reponse.setMessage("Liste des patients");
			reponse.setCode(200);


		}
		catch (Exception e) {
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setMessage("Une erreur interne est survenue");
			reponse.setCode(500);

		}

		return reponse;
	}


	@Override
	public Reponse getUserByEmail(String email) {
		Reponse reponse = new Reponse();
		try
		{
			Optional<Utilisateur>  user = userRepository.findByEmail(email);

			if(user.isPresent())
			{
				UserDtoResponse userConverted =modelMapper.map(user, UserDtoResponse.class);
				reponse.setData(userConverted);
				reponse.setMessage("Ce compte a été retrouvé avec succès");
				reponse.setCode(200);

			}		
			else
			{
				reponse.setMessage("Ce compte n'existe pas");
				reponse.setCode(201);

			}


		}
		catch (Exception e) {
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setMessage("Une erreur interne est survenue");
			reponse.setCode(500);

		}

		return reponse;
	}

	@Override
	public UUID getIdUserByToken(String token)
	{
		// TODO Auto-generated method stub
     	Reponse reponse = new Reponse();
		try
		{
			Optional<Utilisateur>  user = userRepository.findByToken(token);
			if(user.isPresent())
			{

				reponse.setCode(200);
				return  user.get().getID();


			}
			else
			{

				reponse.setCode(201);
				return  null;

			}


		}
		catch (Exception e)
		{
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setCode(500);
			return  null;

		}
	}

	@Override
	public Reponse lockUser(UUID id)
	{Reponse reponse = new Reponse();
		try
		{
			Optional<Utilisateur> user = userRepository.findById(id);

			if(user.isPresent())
			{
				user.get().setStatus(false);
				Utilisateur userSave=userRepository.save(user.get());
				reponse.setData(modelMapper.map(userSave, UserDtoResponse.class));
				reponse.setMessage("Ce compte a été bloqué avec succès");
				reponse.setCode(200);

			}
			else
			{
				reponse.setMessage("Ce compte n'existe pas");
				reponse.setCode(201);

			}


		}
		catch (Exception e) {
			logger.error(" une exception est survenue "+e.getMessage());
			reponse.setMessage("Une erreur interne est survenue");
			reponse.setCode(500);

		}

		return reponse;
	}

	@Override
	public Reponse changePassword(ChangePasswordDtoRequest changePasswordDtoRequest)
	{
		Reponse response = new Reponse();
		try
		{
			Optional<Utilisateur>  user = userRepository.findById(changePasswordDtoRequest.getID());

			if(user.isPresent())
			{

				if((bCryptPasswordEncoder.matches(changePasswordDtoRequest.getOldPassword(), user.get().getPassword())) || changePasswordDtoRequest.getIsAdmin() == 1)
				{
					String pwdCryp = bCryptPasswordEncoder.encode(changePasswordDtoRequest.getNewPassword());
					user.get().setPassword(pwdCryp);
					userRepository.save(user.get());
					response.setCode(200);
					response.setMessage("Le mot de passe a été modifié avec succès : !");
					response.setData(changePasswordDtoRequest.getNewPassword());

				}
				else
				{
					response.setCode(201);
					response.setMessage("L'ancien mot de passe est incorrect !");
				}
			}
			else
			{
				response.setCode(201);
				response.setMessage("Ce compte n'existe pas  !");

			}


		}
		catch (Exception e)
		{
			logger.error(" une exception est survenue "+e.getMessage());
			response.setCode(500);
			response.setMessage("Une erreur serveur est survenue !");
		}    


		return response ;
	
	}


}

