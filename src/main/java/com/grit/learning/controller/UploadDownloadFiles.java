package com.grit.learning.controller;

import com.grit.learning.service.FileResponse;
import com.grit.learning.service.FileStorageSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UploadDownloadFiles {
    @Autowired
    FileStorageSystem fileStorageSystem;

    @PostMapping("/uploadFile")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("file")MultipartFile multipartFile){
        String uploadFile = fileStorageSystem.saveFile(multipartFile);
        String downLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/download").
                path(uploadFile).toUriString();
        return ResponseEntity.status(HttpStatus.OK).body(new FileResponse(uploadFile,downLoadUri,"File uploaded Successfully"));
    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<List<FileResponse>> uploadFiles(@RequestParam("files") MultipartFile [] multipartFiles){
        List<FileResponse> responses = Arrays.asList(multipartFiles).stream().map(file -> {
            String uploadFile = fileStorageSystem.saveFile(file);
            String downLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/download").path(uploadFile).toUriString();
            return new FileResponse(uploadFile,downLoadUri,"File Uploaded successfully");
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable String fileName){
        Resource resource = fileStorageSystem.loadFile(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "Attachements;fileName=\""+resource.getFilename()+"\"").body(resource);
    }
}
