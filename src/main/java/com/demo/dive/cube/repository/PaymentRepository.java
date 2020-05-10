package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findAllByIsDeletedFalse();

    List<Payment> findAllByOrderIdStartingWith(String paymentType);

    @Query("select sum(p.amount) from Payment p where orderId=?1")
    Double getTotalSumByOrderId(String orderId);
}
