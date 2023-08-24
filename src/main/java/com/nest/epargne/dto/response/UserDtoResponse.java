package com.nest.epargne.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class UserDtoResponse
{

    private UUID ID;
    private String lastname;
    private String firstname;
    private String adress;
    private String email;
    private String phone;
    private boolean status;
    private Long birthDay;
    private String typeOfUser;
    private String role;
    private String identification_code;
    private String url_supported;
    private String assurance;
    private Long date_enregistrement;
    private double solde;


}