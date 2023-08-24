package com.nest.epargne.dto.request;


import com.nest.epargne.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class IntouchCallBackDtoRequest
{
    private UUID ID;
    private String service_id;
    private String gu_transaction_id;
    private String status;
    private String partner_transaction_id;
    private String call_back_url;
    private String commission;

}



