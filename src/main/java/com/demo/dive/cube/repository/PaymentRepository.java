package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.SupplierPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<SupplierPayment,Long> {
    List<SupplierPayment> findAllByIsDeletedFalse();
}
