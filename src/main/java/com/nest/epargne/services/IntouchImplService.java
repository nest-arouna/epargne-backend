package com.nest.epargne.services;

import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.dto.request.IntouchCallBackDtoRequest;
import com.nest.epargne.dto.response.CreditDtoResponse;
import com.nest.epargne.entities.Compte;
import com.nest.epargne.entities.Credit;
import com.nest.epargne.entities.IntouchCallBack;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.repositories.IDaoCompte;
import com.nest.epargne.repositories.IDaoCredit;
import com.nest.epargne.repositories.IntouchDaoRepository;
import com.nest.epargne.utils.Utility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IntouchImplService implements IntouchService{
    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Autowired
    private IntouchDaoRepository intouchDaoRepository;



    @Override
    public void intouchCallBack(IntouchCallBackDtoRequest intouchCallBackDtoRequest)
    {

           IntouchCallBack intouchCallBack =modelMapper.map(intouchCallBackDtoRequest, IntouchCallBack.class);
            intouchDaoRepository.save(intouchCallBack);

    }

}
