package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Course;
import com.demo.dive.cube.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public void save(Course course){
        if(course != null){
            if(course.getId() == null){
                courseRepository.save(course);
            }
            else{
                Course courseExist = findOne(course.getId());
                if(courseExist != null){
                    courseExist = course;
                    courseRepository.save(courseExist);
                }
            }
        }
    }

    public List<Course> findAll(){
        return courseRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Course course = findOne(id);
        if(course != null){
            course.setDeleted(true);
            courseRepository.save(course);
        }
    }

    public Course findOne(Long id){
        return courseRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
