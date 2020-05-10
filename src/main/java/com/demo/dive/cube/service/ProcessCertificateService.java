package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.ProcessCertificationDto;
import com.demo.dive.cube.model.BookCourse;
import com.demo.dive.cube.model.ProcessCertification;
import com.demo.dive.cube.repository.ProcessCertificateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessCertificateService {
    @Autowired
    private ProcessCertificateRepository processCertificateRepository;

    @Autowired
    private BookCourseService bookCourseService;

    public ProcessCertificationDto getNewObject(String bookingId){
       BookCourse bookCourse = bookCourseService.findOne(Long.parseLong(bookingId.split("-")[1]));
        return new ProcessCertificationDto(bookingId,bookCourse.getStudent().getName(),bookCourse.getCourse().getCourseName());
    }
    public ProcessCertificationDto getNewObject(Long id){
        ProcessCertification processCertification=findOne(id);
        return new ProcessCertificationDto(processCertification.getBookCourse().getBookingId(),processCertification.getId(),processCertification.getDateOfCertificate(),processCertification.getDateOfProcessing(),processCertification.getCertificationType(),processCertification.getBookCourse().getStudent().getName(),processCertification.getBookCourse().getCourse().getCourseName());
    }

    public List<ProcessCertification> findAll(){
        return processCertificateRepository.findAllByIsDeletedFalse();
    }

    public ProcessCertification findOne(Long id){
        return processCertificateRepository.findByIdAndIsDeletedFalse(id);
    }

    public void saveCertificateProcess(ProcessCertificationDto processCertificationDto){
        ProcessCertification processCertification= new ProcessCertification();
        if(processCertificationDto!=null){
            if(processCertificationDto.getId()!=null){
                processCertification=findOne(processCertificationDto.getId());}
            if(processCertification!=null){
                BeanUtils.copyProperties(processCertificationDto,processCertification);
                processCertification.setCertificationType(processCertificationDto.getCertificationType().ordinal());
                processCertification.setBookCourse(bookCourseService.findOne(Long.parseLong(processCertificationDto.getBookingId().split("-")[1])));
                processCertificateRepository.save(processCertification);
            }

        }
    }

    public void deleteCertificateProcess(Long id){
        ProcessCertification processCertification=findOne(id);
        if(processCertification!=null){
            processCertification.setIsDeleted(true);
            processCertificateRepository.save(processCertification);
        }
    }
}
