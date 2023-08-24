package com.nest.epargne.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import com.nest.epargne.entities.Reponse;
import com.nest.epargne.services.IFileService;
import com.nest.epargne.utils.Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService implements IFileService {
    @Value("${file.upload-dir}")
    private String fileStorageProperties;


    /**
     * UTILITAIRE DE CREATION DU REPERTOIRE (OU SOUS-REPERTOIRE) DE STOCKAGE SI
     * CELUI-CI EST INEXISTANT
     *
     * @param
     * @return
     */
    private Path initStoragePath(String subFolder) {
        Path rootPath = Paths.get(fileStorageProperties + subFolder + File.separatorChar).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootPath);
            return rootPath;
        } catch (IOException e) {
            throw new RuntimeException("Echec de création du dossier de stockage des fichiers.");
        }
    }
    //	delete
    @Override
    public Reponse uploadFile(MultipartFile file)
    {
        Reponse reponse =new Reponse();
        try
        {
            if (file != null &&!file.getOriginalFilename().toLowerCase().endsWith(".pdf")
                    && !file.getOriginalFilename().toLowerCase().endsWith(".PDF"))
            {
                reponse.setCode(201);
                reponse.setMessage(" Ce format de fichier n'est pas autorisé. Réessayez avec un .pdf ou .PDF SVP.");
            }
            else
            {
                Path destinationStockage = this.initStoragePath(Utility.PRISE_EN_CHARGE_FOLDER);//on cree un subFolder dans le folder de storage
                // Initialisation, enregistrement de meta-donnees et stockage du fichier
                String filename = StringUtils.cleanPath(file.getOriginalFilename());// Normalisation du nom du fichier
                destinationStockage = destinationStockage.resolve(filename);//formalise le path concatene du filename
                //transfert du fichier de la source locale vers la destination systeme
                //on le remplace s'il existe un fichier de meme nom

                   Files.copy(file.getInputStream(), destinationStockage, StandardCopyOption.REPLACE_EXISTING);

                reponse.setData(filename);
                reponse.setCode(200);
                reponse.setMessage(" Le fichier  a été bien chargé");
            }


            return reponse ;
        }
        catch (Exception e)
        {

            reponse.setCode(500);
            reponse.setMessage(" Erreur survenue lors de l'enregistrement du fichier.");
            return reponse ;
        }

    }

    @Override
    public boolean deletedFile(String fileName)
    {

        Path destinationStockage = this.initStoragePath(Utility.PRISE_EN_CHARGE_FOLDER);//on cree un subFolder dans le folder de storage
        // Initialisation, enregistrement de meta-donnees et stockage du fichier
        destinationStockage = destinationStockage.resolve(fileName);//formalise le path concatene du filename

        // deleteIfExists File
        try {
            // File to be deleted
            File file = new File(destinationStockage.toString());
            if(file.exists())
            {
                return file.delete();
            }
            else {
                return false;
            }

        }
        catch (Exception e)
        {

            return false;
        }
        finally {
            return false;
        }

    }

    @Override
    public String getPathFile(String filename) {
        Path destinationStockage = this.initStoragePath(Utility.PRISE_EN_CHARGE_FOLDER);//on cree un subFolder dans le folder de storage
        // Initialisation, enregistrement de meta-donnees et stockage du fichier
        destinationStockage = destinationStockage.resolve(filename);//formalise le path concatene du filename

        // deleteIfExists File
        try {
            // File to be deleted
            File file = new File(destinationStockage.toString());
            if(file.exists())
            {
                return file.getAbsolutePath().toString();
            }
            else {
                return null;
            }

        }
        catch (Exception e)
        {

            return null;
        }

    }

    @Override
    public Reponse createAllDirectories() {

        Reponse reponse =new Reponse();
        Path rootPathPriseEnCharge = Paths.get(fileStorageProperties + Utility.PRISE_EN_CHARGE_FOLDER + File.separatorChar).toAbsolutePath().normalize();

        try
        {
            // CREATION DES FICHIERS
            Files.createDirectories(rootPathPriseEnCharge);
            Path filePriseEnCharge = Paths.get(rootPathPriseEnCharge.toString());
            File priseEnCharge = filePriseEnCharge.toFile();

            if(priseEnCharge.exists())
            {
                reponse.setMessage(" Tous les repectoires ");
                reponse.setCode(200);
            }
            else
            {
                reponse.setCode(201);

            }

        } catch (Exception e)
        {
            reponse.setMessage("Une erreur est survenue : "+e.getMessage());
            reponse.setCode(200);

        }
        return reponse;

    }


}