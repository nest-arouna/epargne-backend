package com.nest.epargne.services;

import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.dto.response.CreditDtoResponse;
import com.nest.epargne.entities.Compte;
import com.nest.epargne.entities.Credit;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.entities.Utilisateur;
import com.nest.epargne.repositories.IDaoCompte;
import com.nest.epargne.repositories.IDaoCredit;
import com.nest.epargne.repositories.IDaoUser;
import com.nest.epargne.repositories.IntouchDaoRepository;
import com.nest.epargne.utils.Utility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class CreditService implements ICreditService{

    @Autowired
    private IDaoCompte compteRepository;
    @Autowired
    private IntouchDaoRepository intouchDaoRepository;
    @Autowired
    private IDaoCredit creditRepository;
    @Autowired
    private IDaoUser userRepository;
    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse updateCredit(CreditDtoRequest creditDtoRequest)
    {
        return null;
    }

    @Override
    public Reponse addCredit(CreditDtoRequest creditDtoRequest) {
        Reponse reponse = new Reponse();
        try
        {
                   creditDtoRequest.setStatus(Utility.PENDING);
                   Credit credit =modelMapper.map(creditDtoRequest, Credit.class);

            Credit creditSave = creditRepository.save(credit);
                   reponse.setData(modelMapper.map(creditSave, CreditDtoResponse.class));
                   reponse.setMessage("Le compte a été crédité avec succès");
                   reponse.setCode(200);


        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue" +e.getMessage());
            reponse.setCode(500);

        }

        return reponse;


    }

    @Override
    public Reponse getCreditById(UUID id) {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Credit> credit = creditRepository.findById(id);
            if(credit.isPresent())
            {
                CreditDtoResponse creditConverted =modelMapper.map(credit.get(), CreditDtoResponse.class);
                reponse.setData(creditConverted);
                reponse.setMessage("Le débit a été retrouvé avec succès");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Ce débit n'existe pas");
                reponse.setCode(201);

            }


        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse getAllCreditsByAdminCaisse(Long date_operation, Double montant, UUID patientID, String identifiant_credit, int size, int page) {
        int skipCount = (page - 1) * size;
        Reponse reponse = new Reponse();
        try
        {

            List<Credit> credits = creditRepository.findByOrderByDateOperationDesc() ;

            if(credits.size() > 0)
            {

                HashMap<String, Object> datas  = new HashMap<String, Object>();
                List<CreditDtoResponse> creditFiltereds =credits
                        .stream()
                        .filter(v ->patientID == null ||  v.getPatientID().compareTo(patientID) == 0)
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->identifiant_credit == null || (identifiant_credit != null && identifiant_credit.length() >0 && (v.getIdentifiantCredit().toUpperCase().startsWith(identifiant_credit) || v.getIdentifiantCredit().toLowerCase().startsWith(identifiant_credit))))
                        .skip(skipCount)
                        .limit(size)
                        .map(p->modelMapper.map(p, CreditDtoResponse.class) )
                        .map( g ->{
                            Optional<Utilisateur> patient= userRepository.findById(g.getPatientID());
                            if(patient.isPresent())
                            {
                                g.setPatient(patient.get().getLastname().concat(" ").concat(patient.get().getFirstname()) );
                            }
                            return g;

                        })


                        .collect(Collectors.toList());
                long sizeListFiltered=credits
                        .stream()
                        .filter(v ->patientID == null ||  v.getPatientID().compareTo(patientID) == 0)
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->identifiant_credit == null || (identifiant_credit != null && identifiant_credit.length() >0 && (v.getIdentifiantCredit().toUpperCase().startsWith(identifiant_credit) || v.getIdentifiantCredit().toLowerCase().startsWith(identifiant_credit))))
                        .count();

                datas.put("totalCredits",sizeListFiltered);
                datas.put("data", creditFiltereds);
                reponse.setData(datas);
                reponse.setMessage("Liste des crédits");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("La liste est vide");
                reponse.setCode(201);

            }



        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse getAllCredits(Long date_operation, Double montant, UUID patientID, String identifiant_credit, int size, int page) {
        int skipCount = (page - 1) * size;
        Reponse reponse = new Reponse();
        try
        {

            List<Credit> credits = creditRepository.findByPatientIDOrderByDateOperationDesc(patientID) ;

            if(credits.size() > 0)
            {

                HashMap<String, Object> datas  = new HashMap<String, Object>();
                List<CreditDtoResponse> creditFiltereds =credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->identifiant_credit == null || (identifiant_credit != null && identifiant_credit.length() >0 && (v.getIdentifiantCredit().toUpperCase().startsWith(identifiant_credit) || v.getIdentifiantCredit().toLowerCase().startsWith(identifiant_credit))))

                        .skip(skipCount)
                        .limit(size)
                        .map(p->modelMapper.map(p, CreditDtoResponse.class) )
                        .map( g ->{
                            Optional<Utilisateur> patient= userRepository.findById(g.getPatientID());
                            if(patient.isPresent())
                            {
                                g.setPatient(patient.get().getLastname().concat(patient.get().getFirstname()) );
                            }
                            return g;

                        })
                        .collect(Collectors.toList());


                 long sizeListFiltered=credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->identifiant_credit == null || (identifiant_credit != null && identifiant_credit.length() >0 && (v.getIdentifiantCredit().toUpperCase().startsWith(identifiant_credit) || v.getIdentifiantCredit().toLowerCase().startsWith(identifiant_credit))))
                        .count();

                datas.put("totalCredits",sizeListFiltered);
                datas.put("data", creditFiltereds);
                reponse.setData(datas);
                reponse.setMessage("Liste des crédits");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("La liste est vide");
                reponse.setCode(201);

            }



        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue" +e.getMessage());
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse succesCredit(UUID creditID)
    {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Credit> credit = creditRepository.findById(creditID);
            if(credit.isPresent())
            {
                Optional<Compte> compte= compteRepository.findByPatientID(credit.get().getPatientID());
                if(compte.isPresent())
                {
                    compte.get().setMontant_courant(compte.get().getMontant_courant() + credit.get().getMontant());
                    compte.get().setDate_dernier_recharge(new Date().getTime());
                    compte.get().setMontant_dernier_recharge(credit.get().getMontant());
                    compteRepository.save(compte.get());
                    credit.get().setStatus(Utility.SUCCESS);
                    Credit creditSave = creditRepository.save(credit.get());
                    reponse.setData(modelMapper.map(creditSave, CreditDtoResponse.class));
                    reponse.setMessage("Le compte a été crédité avec succès");
                    reponse.setCode(200);
                }
                else
                {

                    reponse.setMessage("Veuillez reéssayer svp");
                    reponse.setCode(201);
                }




            }
            else
            {
                reponse.setMessage("Ce débit n'existe pas");
                reponse.setCode(201);

            }


        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;

    }

    @Override
    public Reponse failedCredit(UUID creditID) {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Credit> credit = creditRepository.findById(creditID);
            if(credit.isPresent())
            {
                    credit.get().setStatus(Utility.FAILED);
                    Credit creditSave = creditRepository.save(credit.get());
                    reponse.setData(modelMapper.map(creditSave, CreditDtoResponse.class));
                    reponse.setMessage("L'opération a échoué");
                    reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Ce débit n'existe pas");
                reponse.setCode(201);

            }


        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }



}
