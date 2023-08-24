package com.nest.epargne.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Operation
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID ID;
    protected Long dateOperation;
    protected Double montant;
    protected UUID  patientID;
    protected String status;


}
