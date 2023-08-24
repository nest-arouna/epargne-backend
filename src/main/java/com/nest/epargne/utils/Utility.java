package com.nest.epargne.utils;


public final class Utility 
{


	// INCTOUCH STATUS
	public static final String FAILED = "FAILED";
	public static final String SUCCESS = "SUCCESS";
	public static final String PENDING = "PENDING";
	public static final String INTOUCH = "/intouch";



	// CONSTANT POUR LA SECURITE
//	public static final long EXPIRATION_TIME = 1*24* 60 * 60 ; // expire dans un 1 jour

	public static final long EXPIRATION_TIME = 1*2*60*60 ; // expire dans un 2h

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SECRET = "javainuse";
	public static final String PRISE_EN_CHARGE_FOLDER = "PRISE_EN_CHARGE_FOLDER/";
	public static final String TYPE_PATIENT ="PATIENT";
	public static final String TYPE_PROFESSIONNAL ="PROFESSIONNAL";




	public static final String ADD_USER = "/users";
	public static final String ADD_PATIENT = "/patients";
	public static final String ADD_PATIENT_WITH_FILE = "/patients/file";

	public static final String UPDATE_USER = "/users/update";
	public static final String UPDATE_PATIENT_WITH_FILE = "/patients/update/file";

	public static final String GET_USER_BY_ID = "/users/{id}";
	public static final String DELETE_USER_BY_ID = "/users/{id}";
	public static final String GET_ALL_PROFESSIONNALS = "/users/professionnals";
	public static final String GET_ALL_PATIENTS = "/users/patients";
	public static final String COMPTE = "/comptes/{id}";



	public static final String GET_CREDIT_BY_ID = "/credits/{id}";
	public static final String ADD_CREDIT= "/credits";
	public static final String GET_ALL_CREDIT = "/credits/patient/{id}";
	public static final String SUCCESS_CREDIT = "/credits/success/{id}";

	public static final String FAILED_CREDIT = "/credits/failed/{id}";


	public static final String GET_DEBIT_BY_ID = "/debits/{id}";
	public static final String DEBITS= "/debits";
	public static final String GET_ALL_DEBIT_BY_PATIENTS = "/debits/patient/{id}";
	public static final String GET_ALL_DEBIT_BY_PROFESSIONNALS = "/debits/professionnal/{id}";
	public static final String GET_ALL_DEBIT_FOR_PATIENT_BY_PROFESSIONNALS = "/debits/patient/{patientID}/professionnal/{professionnalID}";


	public static final String DO_CONTACTED = "/acceuil/user/contacter";
	public static final String DO_REGISTER = "/user/register";
	public static final String DO_REGISTER_BY_ADMIN = "/user/register/admin";
	public static final String DO_LOGIN = "/login";
	public static final String DO_ACTIVATION = "/activation";

	public static final String DO_FORGOT_PASSWORD = "/forgot";
	public static final String UPDATE_PASSWORD = "/modifierpwd/{id}";
	
	
	// GENERER TOKEN



}
