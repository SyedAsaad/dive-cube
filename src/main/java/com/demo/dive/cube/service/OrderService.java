package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAllByIsDeletedFalse();
    }
}
