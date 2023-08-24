package com.nest.epargne.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nest.epargne.dto.request.UserDtoRequest;
import com.nest.epargne.dto.response.UserDtoResponse;
import com.nest.epargne.entities.Login;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.entities.RoleEmun;
import com.nest.epargne.entities.Utilisateur;
import com.nest.epargne.repositories.IDaoUser;
import com.nest.epargne.security.JwtAuthenticationEntryPoint;
import com.nest.epargne.security.JwtTokenUtil;
import com.nest.epargne.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountRestControler.class)
@Disabled
public class AccountRestControllerTest
{
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private IDaoUser daoUser;

    @Mock
    private UserDetailsService userDetailsService;
    @MockBean
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private ObjectMapper objectMapper;
    private UserDtoRequest user;
    private Utilisateur userC,userC1,userC2;
    private Reponse reponse,reponseList;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UUID id=null;
    private Login login;
    private UserDtoResponse userR;
    private RoleEmun roleEmun;
    @BeforeEach
    void init()
    {

        id= UUID.randomUUID();
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


        reponse = new Reponse();
        reponse.setCode(200);
        reponse.setMessage("la requette a reussi");
        reponse.setData(user);


        List<Utilisateur> list=new ArrayList<>();
        list.add(userC1);
        list.add(userC2);

        reponseList = new Reponse();
        reponseList.setCode(200);
        reponseList.setMessage("la requette a reussi");
        reponseList.setData(list);

    }
    @Test
    @DisplayName("requette   /user/add ")
    void createUser() throws Exception
    {

        when(accountService.login_up(any(UserDtoRequest.class))).thenReturn(reponse);
        when(bCryptPasswordEncoder.matches(login.getPassword(), userC.getPassword())).thenReturn(true);
        when(daoUser.findByPhone("775073511")).thenReturn(Optional.ofNullable(userC));
        when(daoUser.save(any(Utilisateur.class))).thenReturn(userC2);
        when(accountService.getToken(login.getPhone(),login.getPassword())).thenReturn("€€€€hhhhjjjjj");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/add")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }
    @Test @Disabled
    @DisplayName("requette   /user/update ")
    void updateUser() throws Exception
    {
        when(accountService.updateUser(any(UserDtoRequest.class))).thenReturn(reponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/update")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }
    @Test @Disabled
    @DisplayName("requette   /user/users/1 ")
    void getUser() throws Exception
    {
        when(accountService.getUserById(id)).thenReturn(reponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }

    @Test @Disabled
    @DisplayName("requette   /users ")
    void getUsers() throws Exception
    {
      //  when(accountService.getAllUsers()).thenReturn(reponseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }
    @Test @Disabled
    @DisplayName("requette   /users/delete/1 ")
    void deleteUser() throws Exception
    {
        when(accountService.lockUser(id)).thenReturn(reponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }

}
