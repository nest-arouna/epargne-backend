package com.nest.epargne.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class Utilisateur
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    private String lastname;
    private String firstname;
    private String assurance;
    private String adress;
    private String email;
    private String password;
    private String token;
    private String typeOfUser;
    private String phone;
    private Long birthDay;
    @ColumnDefault("0")
    private Long date_enregistrement;
    private boolean status;
    private String role;
    private String identification_code;
    private String url_supported;
}
