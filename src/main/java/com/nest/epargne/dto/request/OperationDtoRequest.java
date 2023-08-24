package com.nest.epargne.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public abstract class OperationDtoRequest
{
    protected UUID ID;
    protected Long dateOperation;
    protected Double montant;
    protected UUID  patientID;
    protected String status;

}
