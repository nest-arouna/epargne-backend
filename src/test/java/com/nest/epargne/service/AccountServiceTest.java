package com.nest.epargne.service;

import com.nest.epargne.dto.request.UserDtoRequest;
import com.nest.epargne.dto.response.UserDtoResponse;
import com.nest.epargne.entities.Login;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.entities.RoleEmun;
import com.nest.epargne.entities.Utilisateur;
import com.nest.epargne.repositories.IDaoUser;
import com.nest.epargne.security.JwtTokenUtil;
import com.nest.epargne.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
public class AccountServiceTest
{
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private AccountService accountService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private IDaoUser daoUser;

    private UserDtoRequest user;
    private UserDtoResponse userR;
    private RoleEmun roleEmun;
    private Login login;
    private Utilisateur userC,userC1,userC2;

    private UUID id=null;

    @BeforeEach
    void init()
    {
        id=UUID.randomUUID();

        login = new Login("775073511","1234" );
        //ARRANGE
        user = new UserDtoRequest();
        user.setID(id);
        user.setEmail("sa@sa");
        user.setPhone("775073511");

        userR = new UserDtoResponse();
        userR.setID(id);
        userR.setEmail("sa1@sa1");
        userR.setPhone("775073512");

        userC = new Utilisateur();
        userC.setID(id);
        userC.setEmail("sa2@sa2");
        userC.setPhone("775073513");

        Utilisateur userC1 = new Utilisateur();
        userC1.setID(id);
        userC1.setEmail("sa3@sa3");
        userC1.setPhone("775073514");

        Utilisateur userC2 = new Utilisateur();
        userC2.setID(id);
        userC2.setEmail("sa4@sa4");
        userC2.setPhone("775073515");


    }

    @Test @Disabled
    @DisplayName("Create d'utilisateur ")
    void createUser()
    {
   //     when(daoUser.findByEmailOrPhone(any(String.class),any(String.class))).thenReturn(Optional.ofNullable(userC1));
        when(daoUser.save(any(Utilisateur.class))).thenReturn(userC2);
        //ACT
        Reponse rep =accountService.login_up(user);
        UserDtoResponse userDtoResponse = (UserDtoResponse) rep.getData();
        //ASSERT
        assertNotNull(rep);
        assertThat(rep.getCode()).isEqualTo(200);



    }
    @Test @Disabled
    @DisplayName("modifier d'utilisateur ")
    void updateUser()
    {
      //  when(daoUser.findByEmailOrPhone(any(String.class),any(String.class))).thenReturn(Optional.ofNullable(null));
        when(daoUser.findById(id)).thenReturn(Optional.ofNullable(userC));
        when(daoUser.save(any(Utilisateur.class))).thenReturn(userC);
        //ACT
        Reponse rep =accountService.updateUser(user);
        //ASSERT
        assertNotNull(rep);
        assertThat(rep.getCode()).isEqualTo(200);
    }
    @Test @Disabled
    @DisplayName("Se  connecter téléphone et mot de passe ")
    void se_connecter()
    {         //ARRANGE

        when(bCryptPasswordEncoder.matches(login.getPassword(), userC.getPassword())).thenReturn(true);
        when(daoUser.findByPhone("775073511")).thenReturn(Optional.ofNullable(userC));
        when(daoUser.save(any(Utilisateur.class))).thenReturn(userC2);
        when(accountService.getToken(login.getPhone(),login.getPassword())).thenReturn("€€€€hhhhjjjjj");
        //ACT
        Reponse rep =accountService.login_in(login);
        //ASSERT
        assertNotNull(rep);
        assertThat(rep.getCode()).isEqualTo(200);

    }

    @Test @Disabled
    @DisplayName("Obtenir   un  utilisateur  par ID")
    void getUserById()
    {

        when(daoUser.findById(id)).thenReturn(Optional.of(userC));
        //ACT
        Reponse rep =accountService.getUserById(id);
        //ASSERT
        assertNotNull(rep);
        assertThat(rep.getCode()).isEqualTo(200);
    }
    @Test @Disabled
    @DisplayName("Obtenir   un  utilisateur par email ")
    void getUserByEmail()
    {
        when(daoUser.findByEmail("san@sa")).thenReturn(Optional.of(userC));
        //ACT
        Reponse rep =accountService.getUserByEmail("san@sa");
        //ASSERT
        assertNotNull(rep);
        assertThat(rep.getCode()).isEqualTo(200);

    }
    @Test @Disabled
    @DisplayName("Obtenir   tous les   utilisateurs ")
    void getAllUsers()
    {

        List<Utilisateur> list=new ArrayList<>();
        list.add(userC1);
        when(daoUser.findAll()).thenReturn(list);
        when(modelMapper.map(userC1, UserDtoResponse.class)).thenReturn(userR);




    }

    @Test @Disabled
    @DisplayName("bloquer un  utilisateur  par ID")
    void disableUserById()
    {

        when(daoUser.findById(id)).thenReturn(Optional.of(userC));
        //ACT
        Reponse rep =accountService.lockUser(id);
        //ASSERT
        assertNotNull(rep);
        assertThat(rep.getCode()).isEqualTo(200);
    }


}
