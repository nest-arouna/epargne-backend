package com.nest.epargne.services;

import com.nest.epargne.dto.request.CreditDtoRequest;
import com.nest.epargne.dto.request.DebitDtoRequest;
import com.nest.epargne.dto.response.CreditDtoResponse;
import com.nest.epargne.dto.response.DebitDtoResponse;
import com.nest.epargne.entities.Compte;
import com.nest.epargne.entities.Credit;
import com.nest.epargne.entities.Debit;
import com.nest.epargne.entities.Reponse;
import com.nest.epargne.repositories.IDaoCompte;
import com.nest.epargne.repositories.IDaoCredit;
import com.nest.epargne.repositories.IDaoDebit;
import com.nest.epargne.repositories.IDaoUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DebitService implements IDebitService{

    @Autowired
    private IDaoCompte compteRepository;
    @Autowired
    private IDaoUser userRepository;
    @Autowired
    private IDaoDebit debitRepository;
    @Autowired
    private IDaoCredit creditRepository;
    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse updateDebit(DebitDtoRequest creditDtoRequest)
    {
        return null;
    }

    @Override
    public Reponse addDebit(DebitDtoRequest debitDtoRequest) {
        Reponse reponse = new Reponse();
        try
        {
               Optional<Compte>  compte= compteRepository.findByPatientID(debitDtoRequest.getPatientID());
                if(compte.isPresent() && debitDtoRequest.getMontant().doubleValue() <= compte.get().getMontant_courant())
                {

                    compte.get().setMontant_courant( compte.get().getMontant_courant() - debitDtoRequest.getMontant().doubleValue() );
                    compte.get().setProfessionnalID(debitDtoRequest.getProfessionnalID());
                    compte.get().setDate_derniere_facturation(new Date().getTime());
                    compte.get().setMotif_derniere_facturation(debitDtoRequest.getMotif());
                    compte.get().setMontant_derniere_facturation(debitDtoRequest.getMontant().doubleValue());
                    compteRepository.save(compte.get());
                    Debit debit =modelMapper.map(debitDtoRequest, Debit.class);
                    debit.setDateOperation(new Date().getTime());
                    Debit creditSave = debitRepository.save(debit);
                    reponse.setData(modelMapper.map(creditSave, DebitDtoResponse.class));
                    reponse.setMessage("Le compte a été décaissé avec succès");
                    reponse.setCode(200);

                }
                else
                {
                    reponse.setMessage("Le solde est suffisant pour faire cette opération");
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
    public Reponse getDebitById(UUID id) {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Debit> credit = debitRepository.findById(id);
            if(credit.isPresent())
            {
                DebitDtoResponse creditConverted =modelMapper.map(credit.get(), DebitDtoResponse.class);
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
    public Reponse getAllDebitsByPatients(Long date_operation, Double montant, UUID patientID, String motif, int size, int page) {
        int skipCount = (page - 1) * size;
        Reponse reponse = new Reponse();
        try
        {

            List<Debit> credits = debitRepository.findByPatientID(patientID);
            if(credits.size() > 0)
            {

                HashMap<String, Object> datas  = new HashMap<String, Object>();
                List<DebitDtoResponse> creditFiltereds =credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->motif == null || (motif != null && motif.length() >0 && (v.getMotif().toUpperCase().startsWith(motif) || v.getMotif().toLowerCase().startsWith(motif))))
                        .skip(skipCount)
                        .limit(size)
                        .sorted(Comparator.comparing(Debit::getDateOperation).reversed())
                        .map(p->
                                {
                                    DebitDtoResponse debit=   modelMapper.map(p, DebitDtoResponse.class);
                                    userRepository.findById(p.getProfessionnalID()).ifPresent( c-> debit.setCaissier(c.getFirstname()+" "+c.getLastname()));
                                    return  debit;
                                }


                        )
                        .collect(Collectors.toList());

                datas.put("totalGeneral",credits.stream().mapToDouble(Debit ::getMontant).sum());
                datas.put("totalFiltered",creditFiltereds.stream().mapToDouble(DebitDtoResponse ::getMontant).sum());
                datas.put("totalCredits",creditFiltereds.size());
                datas.put("data", creditFiltereds);
                reponse.setData(datas);
                reponse.setMessage("Liste des débits");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("La liste est vide");
                reponse.setCode(201);

            }



        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue" );
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse getAllDebitsByProfessionnels(Long date_operation, Double montant, UUID professionnalID, String motif, int size, int page) {
        int skipCount = (page - 1) * size;
        Reponse reponse = new Reponse();
        try
        {

            List<Debit> credits = debitRepository.findByProfessionnalID(professionnalID);
            if(credits.size() > 0)
            {

                HashMap<String, Object> datas  = new HashMap<String, Object>();
                List<DebitDtoResponse> creditFiltereds =credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->motif == null || (motif != null && motif.length() >0 && (v.getMotif().toUpperCase().startsWith(motif) || v.getMotif().toLowerCase().startsWith(motif))))
                        .skip(skipCount)
                        .limit(size)
                        .sorted(Comparator.comparing(Debit::getDateOperation).reversed())
                        .map(p->
                                {
                                    DebitDtoResponse debit=   modelMapper.map(p, DebitDtoResponse.class);
                                    userRepository.findById(p.getProfessionnalID()).ifPresent( c-> debit.setCaissier(c.getFirstname()+" "+c.getLastname()));
                                    return  debit;
                                }


                        )
                        .collect(Collectors.toList());


                long filteredListSize=credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->motif == null || (motif != null && motif.length() >0 && (v.getMotif().toUpperCase().startsWith(motif) || v.getMotif().toLowerCase().startsWith(motif))))
                        .count();

                datas.put("totalGeneral",credits.stream().mapToDouble(Debit ::getMontant).sum());
                datas.put("totalFiltered",creditFiltereds.stream().mapToDouble(DebitDtoResponse ::getMontant).sum());
                datas.put("totalCredits",filteredListSize);
                datas.put("data", creditFiltereds);
                reponse.setData(datas);
                reponse.setMessage("Liste des débits");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("La liste est vide");
                reponse.setCode(201);

            }



        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue" );
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse getAllDebitsForPatientByProfessionnels(Long date_operation, Double montant, UUID patientID, UUID professionnalID, String motif, int size, int page) {
        int skipCount = (page - 1) * size;
        Reponse reponse = new Reponse();
        try
        {

            List<Debit> credits = debitRepository.findByPatientIDAndProfessionnalID(patientID,professionnalID);
            if(credits.size() > 0)
            {

                HashMap<String, Object> datas  = new HashMap<String, Object>();
                List<DebitDtoResponse> creditFiltereds =credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->motif == null || (motif != null && motif.length() >0 && (v.getMotif().toUpperCase().startsWith(motif) || v.getMotif().toLowerCase().startsWith(motif))))
                        .skip(skipCount)
                        .limit(size)
                        .sorted(Comparator.comparing(Debit::getDateOperation).reversed())
                        .map(p->
                                {
                                    DebitDtoResponse debit=   modelMapper.map(p, DebitDtoResponse.class);
                                    userRepository.findById(p.getProfessionnalID()).ifPresent( c-> debit.setCaissier(c.getFirstname()+" "+c.getLastname()));
                                    return  debit;
                                }


                        )
                        .collect(Collectors.toList());


                long filteredListSize=credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->motif == null || (motif != null && motif.length() >0 && (v.getMotif().toUpperCase().startsWith(motif) || v.getMotif().toLowerCase().startsWith(motif))))
                        .count();

                datas.put("totalGeneral",credits.stream().mapToDouble(Debit ::getMontant).sum());
                datas.put("totalFiltered",creditFiltereds.stream().mapToDouble(DebitDtoResponse ::getMontant).sum());
                datas.put("totalCredits",filteredListSize);
                datas.put("data", creditFiltereds);
                reponse.setData(datas);
                reponse.setMessage("Liste des débits");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("La liste est vide");
                reponse.setCode(201);

            }



        }
        catch (Exception e) {
            reponse.setMessage("Une erreur interne est survenue" );
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse getAllDebits(Long date_operation, Double montant, String motif, int size, int page) {
        int skipCount = (page - 1) * size;
        Reponse reponse = new Reponse();

        try
        {

            List<Debit> credits = debitRepository.findAll();
            if(credits.size() > 0)
            {
                HashMap<String, Object> datas  = new HashMap<String, Object>();
                List<DebitDtoResponse> creditFiltereds =credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->motif == null || (motif != null && motif.length() >0 && v.getMotif().startsWith(motif) ))
                        .skip(skipCount)
                        .limit(size)
                        .sorted(Comparator.comparing(Debit::getDateOperation).reversed())
                        .map(p->
                                {
                                    DebitDtoResponse debit=   modelMapper.map(p, DebitDtoResponse.class);
                                    userRepository.findById(p.getProfessionnalID()).ifPresent( c-> debit.setCaissier(c.getFirstname()+" "+c.getLastname()));
                                    return  debit;
                                }


                        )
                        .collect(Collectors.toList());

                long filteredListSize=credits
                        .stream()
                        .filter(v ->date_operation == null || (date_operation != null && date_operation.longValue() >0 && ((date_operation.longValue()+24*60*60*1000) > v.getDateOperation().longValue()) && (v.getDateOperation().longValue() >= date_operation.longValue())))
                        .filter(v ->montant == null || (montant != null && montant.longValue() >0 && v.getMontant().longValue() == montant.longValue()))
                        .filter(v ->motif == null || (motif != null && motif.length() >0 && (v.getMotif().toUpperCase().startsWith(motif) || v.getMotif().toLowerCase().startsWith(motif))))
                        .count();

                datas.put("totalGeneral",credits.stream().mapToDouble(Debit ::getMontant).sum());
                datas.put("totalFiltered",creditFiltereds.stream().mapToDouble(DebitDtoResponse ::getMontant).sum());
                datas.put("totalCredits",filteredListSize);
                datas.put("data", creditFiltereds);
                reponse.setData(datas);
                reponse.setMessage("Liste des débits");
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


}
