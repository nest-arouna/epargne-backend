package com.nest.epargne.dto.response;


import com.nest.epargne.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class CreditDtoResponse extends Operation
{
    private String identifiantCredit;

    private String patient;

}
