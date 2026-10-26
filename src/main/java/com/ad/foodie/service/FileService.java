package com.ad.foodie.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.ad.foodie.dto.FileData;



public interface FileService {

    FileData uploadFile(MultipartFile file,String path);

    
    void deleteFile(String path);

    Resource loadfile(String path);
}
