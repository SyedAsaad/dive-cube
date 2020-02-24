package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.DiveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiveTypeRepository extends JpaRepository<DiveType,Long> {

    List<DiveType> findAllByIsDeletedFalse();
}
