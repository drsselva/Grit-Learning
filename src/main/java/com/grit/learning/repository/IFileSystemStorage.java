package com.grit.learning.repository;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;



public interface IFileSystemStorage {

    void init();
    String saveFile(MultipartFile fileName);
    Resource loadFile(String fileName);
}
