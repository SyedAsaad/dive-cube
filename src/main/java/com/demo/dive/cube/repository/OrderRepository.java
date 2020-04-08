package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    public Order findOneByIdAndIsDeletedFalse(Long id);
    public List<Order> findAllByIsDeletedFalse();

    @Query(value = "SELECT MAX(seq.next_val) FROM hibernate_sequence seq",nativeQuery = true)
    Long getHighestId();
}
