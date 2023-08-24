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
public class Compte
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    private long date_derniere_facturation;
    @ColumnDefault("0")
    private long date_dernier_recharge;
    @ColumnDefault("0")
    private double montant_dernier_recharge ;
    private double montant_courant;
    private double montant_derniere_facturation;
    private String motif_derniere_facturation;
    private UUID  professionnalID;
    private UUID  patientID;
}
