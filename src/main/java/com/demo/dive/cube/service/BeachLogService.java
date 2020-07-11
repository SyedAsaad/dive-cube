package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Queries;
import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.dto.BeachLogDto;
import com.demo.dive.cube.dto.BeachLogUserDto;
import com.demo.dive.cube.enums.CertificationLevel;
import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.jrbeans.BeachLogJrBean;
import com.demo.dive.cube.model.*;
import com.demo.dive.cube.repository.BeachLogRepository;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BeachLogService {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private BeachLogRepository beachLogRepository;


    @Autowired
    private EnumService enumService;


    @Autowired
    private ReportService reportService;

    @PersistenceContext
    public EntityManager em;

    private static String[] columnName = { "Name" ,"Certificate Level","Site Name"};

    public List<Instructor> findAllInstructors(){
       return instructorService.findAll();
    }

    public BeachLog findOne(Long id){
        return beachLogRepository.findByIdAndIsDeletedFalse(id);
    }

    public LinkedHashSet<BeachLog> findAll(){
        return beachLogRepository.findAllByIsDeletedFalse();
    }

    public void save(BeachLogDto beachLogDto){
        BeachLog existBeachLog = new BeachLog();
        if(beachLogDto.getId()!=null){
            existBeachLog = findOne(beachLogDto.getId());
        }
        if(existBeachLog!=null){
            BeanUtils.copyProperties(beachLogDto,existBeachLog);
            existBeachLog.setDiveName(beachLogDto.getDiveName().ordinal());
            existBeachLog.removeAllUserLogs();
            for(BeachLogUserDto boatLogUserDto : beachLogDto.getBeachLogUserDtos()){
                if(boatLogUserDto.getName()!=null && boatLogUserDto.getCertificationLevel()!=null) {
                    BeachUserLog boatUserLog = new BeachUserLog();
                    BeanUtils.copyProperties(boatLogUserDto, boatUserLog);
                    boatUserLog.setCertificationLevel(boatLogUserDto.getCertificationLevel().ordinal());
                    existBeachLog.addBoatUserLogs(boatUserLog);
                }
            }
            beachLogRepository.save(existBeachLog);
        }

    }

    public BeachLogDto getDto(BeachLog beachLog){
        BeachLogDto beachLogDto = new BeachLogDto();
        BeanUtils.copyProperties(beachLog,beachLogDto);
        for(BeachUserLog beachUserLog : beachLog.getBeachUserLogs()) {
            BeachLogUserDto beachLogUserDto = new BeachLogUserDto();
            BeanUtils.copyProperties(beachUserLog,beachLogUserDto);
            beachLogUserDto.setCertificationLevel(beachUserLog.getCertificationLevel());
            beachLogDto.addBeachUserLogs(beachLogUserDto);
            Collections.sort(beachLogDto.getBeachLogUserDtos(), Comparator.comparingLong(BeachLogUserDto::getId));
        }
        return beachLogDto;
    }

    public void delete(Long id){
        BeachLog beachLog = findOne(id);
        if(beachLog!=null) {
            beachLog.setIsDeleted(Boolean.TRUE);
            beachLogRepository.save(beachLog);
        }
    }

    public void addDependentDetais(ModelAndView modelAndView) {
        modelAndView.addObject("instructors",instructorService.findAll());
        modelAndView.addObject("dives",enumService.getEnumList(DiveName.values()));
        modelAndView.addObject("beachLogList",findAll().stream().map(boatLog -> getDto(boatLog)).collect(Collectors.toSet()));
    }

    public List<BeachLogJrBean> getRequiredData(HttpServletRequest request, HttpServletResponse response) {

        String criteria = "";
        int parameterNo = 1;
        Map<Integer,Object> parameters = new LinkedHashMap<>();

        if(request.getParameter("diveName") != null && !request.getParameter("diveName").toString().isEmpty()){
            criteria += " AND a.dive_name = ?";
            int diveOrdinal = DiveName.valueOf(request.getParameter("diveName").toString()).ordinal();
            parameters.put(parameterNo,diveOrdinal);
            parameterNo++;
        }
        if(request.getParameter("fromDate") != null && !request.getParameter("fromDate").toString().isEmpty() && request.getParameter("toDate") != null && !request.getParameter("toDate").toString().isEmpty()){
            criteria += " AND STR_TO_DATE(a.log_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')";
            parameters.put(parameterNo,request.getParameter("fromDate"));
            parameterNo++;
            criteria += " AND STR_TO_DATE(a.log_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')";
            parameters.put(parameterNo,request.getParameter("toDate"));
            parameterNo++;
        }

        Query query = em.createNativeQuery(Queries.beachLog.replace("criteria",criteria));
        List<Object> results = UtilService.toParameterized(query,parameters,parameterNo).getResultList();

        List<BeachLogJrBean> data = new ArrayList<>();

        for(Object object: results){
            int i = 0;
            Object[] obj = (Object[])object;
            BeachLogJrBean beachLogJrBean = new BeachLogJrBean();
            beachLogJrBean.setLogDate(UtilService.isValid(obj[i++]));
            beachLogJrBean.setPersonName(UtilService.isValid(obj[i++]));
            beachLogJrBean.setPersonCert(CertificationLevel.values()[(Integer)obj[i++]].getTitle());
            beachLogJrBean.setDiveType(DiveName.values()[(Integer)obj[i++]].getTitle());
            beachLogJrBean.setInstructorName(UtilService.isValid(obj[i++]));
            beachLogJrBean.setSite(UtilService.isValid(obj[i++]));
            data.add(beachLogJrBean);
        }
        return data;

    }


    public ByteArrayInputStream exportExcel(List<BeachLogJrBean> data, String sheetname) {
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = UtilService.initializeExcel(workbook,columnName,sheetname);

            // Create Other rows and cells with contacts data
            int rowCount = 1;
            for (BeachLogJrBean beach : data) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                row.createCell(columnCount++).setCellValue(beach.getPersonName());
                row.createCell(columnCount++).setCellValue(beach.getPersonCert());
                row.createCell(columnCount++).setCellValue(beach.getSite());
            }

            for (int i = 0; i < columnName.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public ByteArrayInputStream excelReport(HttpServletRequest request, HttpServletResponse response) {
        List<BeachLogJrBean> beachLogJrBeans=getRequiredData(request,response);
        return exportExcel(beachLogJrBeans,"beachLog");

    }

    public void pdfReport(HttpServletRequest request, HttpServletResponse response) {
        List<BeachLogJrBean> beachLogJrBeans=getRequiredData(request,response);
         reportService.export(beachLogJrBeans, ReportConstants.BEACH_LOG_REPORT,request,response);
    }
}
