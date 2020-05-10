package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,Long> {

    Shift findByIdAndIsDeletedFalse(Long id);
    List<Shift> findAllByIsDeletedFalse();
}
