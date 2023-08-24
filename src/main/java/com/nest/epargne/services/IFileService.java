package com.nest.epargne.services;

import com.nest.epargne.entities.Reponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService
{
    public Reponse uploadFile(MultipartFile file) ;
    public boolean deletedFile(String filename) ;

    public String getPathFile(String filename);
    public Reponse createAllDirectories();
}
