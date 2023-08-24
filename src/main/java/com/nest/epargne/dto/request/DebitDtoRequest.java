package com.nest.epargne.dto.request;


import com.nest.epargne.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class DebitDtoRequest extends Operation
{
    private UUID professionnalID;
    private String motif;

}



