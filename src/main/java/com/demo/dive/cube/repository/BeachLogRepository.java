package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.BeachLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedHashSet;
import java.util.List;

public interface BeachLogRepository extends JpaRepository<BeachLog,Long> {

    BeachLog findByIdAndIsDeletedFalse(Long id);
    LinkedHashSet<BeachLog> findAllByIsDeletedFalse();
}
