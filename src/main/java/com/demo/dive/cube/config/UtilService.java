package com.demo.dive.cube.config;

import com.demo.dive.cube.config.exception.BadRequestException;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Query;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.*;

public class UtilService {




    public static Boolean isValidFile(MultipartFile file, Long minSize, Long maxSize, List<String> extension){

        if(file.getContentType() != null && file.getSize() > minSize && file.getSize() <= maxSize){
            String ext = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
            if(extension.stream().filter(fileExt -> fileExt.equalsIgnoreCase(ext)).findAny().isPresent()) return true;
        }
        return false;
    }

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

    public static Query toParameterized(Query query, Map<Integer,Object> parameters, int parameterNo){
        for(int i=1; i<parameterNo; i++){
            query.setParameter(i,parameters.get(i));
        }
        return query;
    }

    public static String isValid(Object str){
        return str == null ? "" : str.toString();
    }

    public static void createFolder(String dirPath){
        File file = new File(dirPath);
        if(!file.exists())
            file.mkdir();
    }

    public static Sheet initializeExcel(Workbook workbook, String[] columnName, String sheetName){

        Sheet sheet = workbook.createSheet(sheetName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);


        for (int i = 0; i < columnName.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnName[i]);
            cell.setCellStyle(headerCellStyle);
            sheet.autoSizeColumn(i);
        }
        return sheet;

    }
}
