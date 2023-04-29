package com.grit.learning.service;

import com.grit.learning.config.FileUploadProperties;
import com.grit.learning.exception.FileNotFoundException;
import com.grit.learning.exception.FileStorageException;
import com.grit.learning.repository.IFileSystemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageSystem implements IFileSystemStorage {
    private final Path dirLocation;

    @Autowired
    public FileStorageSystem(FileUploadProperties fileUploadProperties){
        this.dirLocation = Paths.get(fileUploadProperties.getLocation()).toAbsolutePath().normalize();
    }

    @Override
    @PostConstruct
    public void init() {
        try{
            Files.createDirectories(this.dirLocation);
        } catch(Exception ex){
            throw new FileStorageException("Could not created upload Dir");
        }
    }

    @Override
    public String saveFile(MultipartFile multipartFile) {
        try{
            String fileName = multipartFile.getOriginalFilename();
            Path dFile = this.dirLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(),dFile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch(Exception ex){
            throw new FileStorageException("Could not upload file");
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try{
            Path dFile = this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(dFile.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new FileStorageException("Could not loaded successfully");
            }
        } catch(MalformedURLException ex){
            throw new FileNotFoundException("Could not loaded successfully");
        }

    }
}
