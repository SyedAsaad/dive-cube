package com.demo.dive.cube.service;

import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.config.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService {


    @Value("${student.file.path}")
    private String fileLocation;
    /**
     * upload employee image to server
     * @param file
     * @param fileName
     * @return
     */
    private String uploadEmployeeImage(MultipartFile file, String fileName){
        String fullFileName = fileName + "_image.jpg";
        Boolean isFileUploaded = UtilService.uploadFile(file, fileLocation, fullFileName);
        if(!isFileUploaded) throw new BadRequestException("file.upload.error");
        return fullFileName;
    }

    /**
     * delete employee image
     * @param fileName
     */
    private void deleteEmployeeImage(String fileName){
        String fullFileName = fileName + "_image.jpg";
        UtilService.deleteFileIfExist(fileLocation, fullFileName);
    }
}
