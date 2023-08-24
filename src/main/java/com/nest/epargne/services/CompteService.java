package com.nest.epargne.services;

import com.nest.epargne.dto.response.CreditDtoResponse;
import com.nest.epargne.entities.Compte;
import com.nest.epargne.entities.Credit;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.repositories.IDaoCompte;
import com.nest.epargne.repositories.IDaoCredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompteService implements ICompteService{

    @Autowired
    private IDaoCompte compteRepository;
    @Autowired
    private IDaoCredit creditRepository;

    @Override
    public Reponse etatCompte(UUID id) {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Compte> compte= compteRepository.findByPatientID(id);
            List<Credit> credits = creditRepository.findByPatientIDOrderByDateOperationDesc(id)
                    .stream().sorted(Comparator.comparing(Credit::getDateOperation).reversed()).limit(2).collect(Collectors.toList());
            if(compte.isPresent())
            {
                HashMap<String, Object> datas  = new HashMap<String, Object>();
                datas.put("date_derniere_facturation",compte.get().getDate_derniere_facturation());
                datas.put("date_dernier_recharge",compte.get().getDate_dernier_recharge());
                datas.put("montant_dernier_recharge",compte.get().getMontant_dernier_recharge());
                datas.put("montant_courant",compte.get().getMontant_courant());
                datas.put("montant_derniere_facturation",compte.get().getMontant_derniere_facturation());
                datas.put("motif_derniere_facturation",compte.get().getMotif_derniere_facturation());
                datas.put("data", credits);
                reponse.setData(datas);
                reponse.setMessage("Etat du solde actuel");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Ce compte ne contient pas de dossier de facturation" );
                reponse.setCode(201);
            }


        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue" );
            reponse.setCode(500);

        }

        return reponse;



    }
}
