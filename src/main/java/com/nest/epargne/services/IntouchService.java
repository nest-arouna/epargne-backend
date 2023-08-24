package com.nest.epargne.services;
import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.dto.request.IntouchCallBackDtoRequest;
import com.nest.epargne.entities.Reponse;

import java.util.UUID;

public interface IntouchService {
      // SERVICE CREDIT
	  public void intouchCallBack(IntouchCallBackDtoRequest intouchCallBackDtoRequest);

	 }
