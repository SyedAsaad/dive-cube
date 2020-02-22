package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.SupplierPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<SupplierPayment,Long> {
}
