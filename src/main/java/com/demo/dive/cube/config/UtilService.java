package com.demo.dive.cube.config;

import com.demo.dive.cube.config.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Objects;

public class UtilService {




    public static Boolean uploadFile(MultipartFile fileData, String dirPath, String fileName) throws BadRequestException {
        try{
//            logger.info("Utility.uploadFile: Uploading Image...");

            byte[] btDataFile = fileData.getBytes();
            File file = new File(dirPath);
            if(!file.exists())
                file.mkdir();

            file = new File( dirPath + "/" + fileName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(btDataFile);
            stream.close();

            return true;
        }
        catch(Exception e){
//            logger.error("Error in uploading image. " + e.getMessage());
            return false;
        }
    }

    public static Boolean isValidFile(MultipartFile file, Long minSize, Long maxSize, String extension){

//        logger.info("Utility.isValidFile: Started");

        if(file.getContentType() != null && file.getSize() > minSize && file.getSize() <= maxSize){
            String ext = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
            if(extension.toLowerCase().equals(ext.toLowerCase())) return true;
        }
//        logger.info("Utility.isValidFile: Invalid File");
        return false;
    }

    public static byte[] fetchImageFromDirectory(String filename, String dirPath) {

//        logger.info("Utility.fetchImageFromDirectory: Started");

        try{
//            logger.info("Utility.fetchImageFromDirectory: Check if File exist. Return file bytes or Null");

            File file = new File(dirPath + filename);
            return Files.readAllBytes(file.toPath());
        }
        catch(Exception e){
//            logger.error("Utility.fetchImageFromDirectory: File Not Found On Server");
        }
        return null;
    }

    public static void deleteFileIfExist(String dirPath, String fileName){
        try{
//            logger.info("Utility.deleteFileIfExist: Started");
            File file = new File( dirPath + fileName);
            Files.deleteIfExists(file.toPath());
        }
        catch(Exception e){
//            logger.error("Error in deleting image. " + e.getMessage());
        }
    }
}
