package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.BoatLogDto;
import com.demo.dive.cube.dto.BoatLogUserDto;
import com.demo.dive.cube.enums.CertificationLevel;
import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.model.BoatLog;
import com.demo.dive.cube.model.BoatUserLog;
import com.demo.dive.cube.model.Instructor;
import com.demo.dive.cube.repository.BoatLogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoatLogService {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private BoatLogRepository boatLogRepository;


    @Autowired
    private EnumService enumService;

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
            boatLogUserDto.setCertificationLevel(CertificationLevel.DIVE_MASTER);
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
}
