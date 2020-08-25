package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Queries;
import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.dto.BoatLogDto;
import com.demo.dive.cube.dto.BoatLogUserDto;
import com.demo.dive.cube.enums.CertificationLevel;
import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.jrbeans.BoatLogJrBean;
import com.demo.dive.cube.model.BoatLog;
import com.demo.dive.cube.model.BoatUserLog;
import com.demo.dive.cube.model.Instructor;
import com.demo.dive.cube.repository.BoatLogRepository;
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
public class BoatLogService {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private BoatLogRepository boatLogRepository;

    @PersistenceContext
    public EntityManager em;

    @Autowired
    private EnumService enumService;

    @Autowired
    private ReportService reportService;

    private static String[] columnName = { "Check Site Name" ,"Log Date","Dive","Staff Name","Name","Certification Level","Boat Captain","Instructor Name"};

    public List<Instructor> findAllInstructors(){
       return instructorService.findAll();
    }

    public BoatLog findOne(Long id){
        return boatLogRepository.findByIdAndIsDeletedFalse(id);
    }

    public LinkedHashSet<BoatLog> findAll(){
        return boatLogRepository.findAllByIsDeletedFalse();
    }

    public void save(BoatLogDto boatLogDto){
        BoatLog existBoatLog = new BoatLog();
        if(boatLogDto.getId()!=null){
            existBoatLog = boatLogRepository.findByIdAndIsDeletedFalse(boatLogDto.getId());
        }
        if(existBoatLog!=null){
            BeanUtils.copyProperties(boatLogDto,existBoatLog);
            existBoatLog.setDiveName(boatLogDto.getDiveName().ordinal());
            existBoatLog.removeAllUserLogs();
            for(BoatLogUserDto boatLogUserDto : boatLogDto.getBoatLogUserDtoList()){
                if(boatLogUserDto.getName()!=null && boatLogUserDto.getCertificationLevel()!=null) {
                    BoatUserLog boatUserLog = new BoatUserLog();
                    BeanUtils.copyProperties(boatLogUserDto, boatUserLog);
                    boatUserLog.setCertificationLevel(boatLogUserDto.getCertificationLevel().ordinal());
                    existBoatLog.addBoatUserLogs(boatUserLog);
                }
            }
            boatLogRepository.save(existBoatLog);
        }

    }

    public BoatLogDto getDto(BoatLog boatLog){
        BoatLogDto boatLogDto = new BoatLogDto();
        BeanUtils.copyProperties(boatLog,boatLogDto);
        for(BoatUserLog boatUserLog : boatLog.getBoatUserLogs()) {
            BoatLogUserDto boatLogUserDto = new BoatLogUserDto();
            BeanUtils.copyProperties(boatUserLog,boatLogUserDto);
            boatLogUserDto.setCertificationLevel(boatUserLog.getCertificationLevel());
//            boatLogUserDto.setLastDiveDate(boatUserLog.get);
            boatLogDto.addBoatUserLogs(boatLogUserDto);
            Collections.sort(boatLogDto.getBoatLogUserDtoList(), Comparator.comparingLong(BoatLogUserDto::getId));
        }
        return boatLogDto;
    }

    public void delete(Long id){
        BoatLog boatLog = findOne(id);
        if(boatLog!=null)
            boatLog.setIsDeleted(Boolean.TRUE);
        boatLogRepository.save(boatLog);
    }

    public void addDependentDetais(ModelAndView modelAndView) {
        modelAndView.addObject("instructors",instructorService.findAll());
        modelAndView.addObject("dives",enumService.getEnumList(DiveName.values()));
        modelAndView.addObject("certificateLevels",enumService.getEnumList(CertificationLevel.values()));
        modelAndView.addObject("boatLogList",findAll().stream().map(boatLog -> getDto(boatLog)).collect(Collectors.toList()));
    }

    public void exportReport(HttpServletRequest request, HttpServletResponse response) {
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

        Query query = em.createNativeQuery(Queries.boatLog+criteria);
        List<Object> results = UtilService.toParameterized(query,parameters,parameterNo).getResultList();

        List<BoatLogJrBean> data = new ArrayList<>();

        for(Object object: results){
            int i = 0;
            Object[] obj = (Object[])object;
            BoatLogJrBean boatLogJrBean = new BoatLogJrBean();
            boatLogJrBean.setCheckInSite(UtilService.isValid(obj[i++]));
            boatLogJrBean.setLogDate(UtilService.isValid(obj[i++]));
            boatLogJrBean.setDiveName(DiveName.values()[(int)obj[i++]].getTitle());
            boatLogJrBean.setStaffName(UtilService.isValid(obj[i++]));
            boatLogJrBean.setName(UtilService.isValid(obj[i++]));
            boatLogJrBean.setCertificationLevel(CertificationLevel.values()[(int)obj[i++]].getTitle());
            boatLogJrBean.setBoatCaptain(UtilService.isValid(obj[i++]));
            boatLogJrBean.setInstructorName(UtilService.isValid(obj[i++]));
            data.add(boatLogJrBean);
        }

        reportService.export(data, ReportConstants.BOAT_LOG_REPORT,request,response);
    }

    public ByteArrayInputStream excelReport(HttpServletRequest request, HttpServletResponse response) {
        List<BoatLogJrBean> boatLogJrBeans=exportExcelReport(request,response);
        return exportExcel(boatLogJrBeans,"boatLog");

    }

    public ByteArrayInputStream exportExcel(List<BoatLogJrBean> data, String sheetname) {
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = UtilService.initializeExcel(workbook,columnName,sheetname);

            // Create Other rows and cells with contacts data
            int rowCount = 1;
            for (BoatLogJrBean boat : data) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                row.createCell(columnCount++).setCellValue(boat.getCheckInSite());
                row.createCell(columnCount++).setCellValue(boat.getLogDate());
                row.createCell(columnCount++).setCellValue(boat.getDiveName());
                row.createCell(columnCount++).setCellValue(boat.getStaffName());
                row.createCell(columnCount++).setCellValue(boat.getName());
                row.createCell(columnCount++).setCellValue(boat.getCertificationLevel());
                row.createCell(columnCount++).setCellValue(boat.getBoatCaptain());
                row.createCell(columnCount++).setCellValue(boat.getInstructorName());
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

    public List<BoatLogJrBean> exportExcelReport(HttpServletRequest request, HttpServletResponse response) {
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

        Query query = em.createNativeQuery(Queries.boatLog+criteria);
        List<Object> results = UtilService.toParameterized(query,parameters,parameterNo).getResultList();

        List<BoatLogJrBean> data = new ArrayList<>();

        for(Object object: results){
            int i = 0;
            Object[] obj = (Object[])object;
            BoatLogJrBean boatLogJrBean = new BoatLogJrBean();
            boatLogJrBean.setCheckInSite(UtilService.isValid(obj[i++]));
            boatLogJrBean.setLogDate(UtilService.isValid(obj[i++]));
            boatLogJrBean.setDiveName(DiveName.values()[(int)obj[i++]].getTitle());
            boatLogJrBean.setStaffName(UtilService.isValid(obj[i++]));
            boatLogJrBean.setName(UtilService.isValid(obj[i++]));
            boatLogJrBean.setCertificationLevel(CertificationLevel.values()[(int)obj[i++]].getTitle());
            boatLogJrBean.setBoatCaptain(UtilService.isValid(obj[i++]));
            boatLogJrBean.setInstructorName(UtilService.isValid(obj[i++]));
            data.add(boatLogJrBean);
        }

        return data;
    }
}
