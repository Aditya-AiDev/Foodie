package com.ad.foodie.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ad.foodie.dto.FileData;
import com.ad.foodie.exception.InvalidFilePathException;
import com.ad.foodie.service.FileService;


@Service 
public class FileUploadServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public FileData uploadFile(MultipartFile file, String path) {
        
        if(path.isBlank()){
            throw new InvalidFilePathException("Invalid File Path");
        }
        
        String contentType=file.getContentType();
        /// validation for content
        if(contentType.equals("image/jpg") || contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif")){

        }else{
            throw new InvalidFilePathException();
        }

        


        ///uploads/65465465.png  --> // folder + (time + .extension)
        /// only folder path
        Path folderPath=Paths.get(path.substring(0,path.lastIndexOf("/")+1));

        
        String fileName=path.substring(path.lastIndexOf("/")+1);
        String extension=fileName.substring(fileName.lastIndexOf(".")+1); 
        
        // validation for extension
        if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif")){

        }else{
            throw new InvalidFilePathException();
        }



        logger.info(folderPath.toString());

        //if folder not exists it will create it...
        if(!Files.exists(folderPath)){
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //whole file Path// (folder/file.extension) ---->>> folder + (time + .extension)
        Path filePath=Paths.get(path);
        //file.copy();
        try {
            Files.copy(file.getInputStream(), filePath,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        FileData fileData=new FileData(fileName, path);

        return fileData;

    }

    @Override
    public void deleteFile(String path) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFile'");
    }

    @Override
    public Resource loadfile(String path) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadfile'");
    }

}
