package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    public Supplier findOneByIdAndIsDeletedFalse(Long id);
    public List<Supplier> findAllByIsDeletedFalse();
}
