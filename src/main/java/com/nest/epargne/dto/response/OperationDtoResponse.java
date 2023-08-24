package com.nest.epargne.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class OperationDtoResponse
{
    protected UUID ID;
    protected Long dateOperation;
    protected Double montant;
    protected UUID  patientID;
    protected String motif;

}
