package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT max(st.id) FROM Student st")
    Long getLatestId();

    List<Student> findAllByIsDeletedFalse();
    Student findByIdAndIsDeletedFalse(Long id);

    List<Student> findByFirstNameOrMiddleNameOrLastNameAndIsDeletedFalse(String fname,String mname, String lname);
}
