package com.nest.epargne.dto.response;


import com.nest.epargne.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class DebitDtoResponse extends Operation
{
    private UUID professionnalID;
    private String motif;
    private String caissier;

}
