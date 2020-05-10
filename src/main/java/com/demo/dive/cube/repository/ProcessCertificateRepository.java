package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.ProcessCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessCertificateRepository extends JpaRepository<ProcessCertification,Long> {

    List<ProcessCertification> findAllByIsDeletedFalse();

    ProcessCertification findByIdAndIsDeletedFalse(Long id);

    ProcessCertification findByIsDeletedFalseAndBookCourse_Id(Long bookingId);
}
