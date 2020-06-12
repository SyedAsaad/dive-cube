package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.BeachLogDto;
import com.demo.dive.cube.dto.BeachLogUserDto;
import com.demo.dive.cube.dto.BoatLogDto;
import com.demo.dive.cube.dto.BoatLogUserDto;
import com.demo.dive.cube.enums.CertificationLevel;
import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.model.*;
import com.demo.dive.cube.repository.BeachLogRepository;
import com.demo.dive.cube.repository.BoatLogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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

}
