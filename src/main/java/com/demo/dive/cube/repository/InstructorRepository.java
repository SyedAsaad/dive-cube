package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    public Instructor findOneByIdAndIsDeletedFalse(Long id);
    public List<Instructor> findAllByIsDeletedFalse();
}
