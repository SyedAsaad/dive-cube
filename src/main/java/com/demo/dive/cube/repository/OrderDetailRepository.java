package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    public OrderDetail findOneByIdAndIsDeletedFalse(Long id);
    public List<OrderDetail> findAllByIsDeletedFalse();
    public List<OrderDetail> findAllByOrderAndIsDeletedFalse(Order order);
}
