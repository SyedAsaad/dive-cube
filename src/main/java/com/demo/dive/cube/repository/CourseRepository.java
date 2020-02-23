package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    public Course findOneByIdAndIsDeletedFalse(Long id);
    public List<Course> findAllByIsDeletedFalse();
}
