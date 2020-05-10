package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Instructor;
import com.demo.dive.cube.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public void save(Instructor instructor){
        if(instructor != null){
            if(instructor.getId() == null){
                instructorRepository.save(instructor);
            }
            else{
                Instructor instructorExist = findOne(instructor.getId());
                if(instructorExist != null){
                    instructorExist = instructor;
                    instructorRepository.save(instructorExist);
                }
            }
        }
    }

    public List<Instructor> findAll(){
        return instructorRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Instructor instructor = findOne(id);
        if(instructor != null){
            instructor.setIsDeleted(true);
            instructorRepository.save(instructor);
        }
    }

    public Instructor findOne(Long id){
        return instructorRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
