package com.nest.epargne.dto.response;


import com.nest.epargne.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class CompteDtoResponse extends Operation
{
    private UUID ID;
    private long date_derniere_facturation;
    private long date_dernier_recharge;
    private double montant_dernier_recharge;
    private double montant_courant;
    private double montant_derniere_facturation;
    private String motif_derniere_facturation;
    private UUID  professionnalID;
    private UUID  patientID;

}
