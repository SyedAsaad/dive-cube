package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.BoatLog;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedHashSet;
import java.util.List;

public interface BoatLogRepository extends JpaRepository<BoatLog,Long> {

    BoatLog findByIdAndIsDeletedFalse(Long id);
    LinkedHashSet<BoatLog> findAllByIsDeletedFalse();
}
