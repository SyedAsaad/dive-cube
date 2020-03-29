package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Constants;
import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void save(Order order){
        if(order != null){
            if(order.getId() == null){
                orderRepository.save(order);
            }
            else{
                Order orderExist = findOne(order.getId());
                if(orderExist != null){
                    orderExist = order;
                    orderRepository.save(orderExist);
                }
            }
        }
    }

    public List<Order> findAll(){
        return orderRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Order order = findOne(id);
        if(order != null){
            order.setIsDeleted(true);
            orderRepository.save(order);
        }
    }

    public Order findOne(Long id){
        return orderRepository.findOneByIdAndIsDeletedFalse(id);
    }

    public List<String> getAllPaymentMethods(){
        List<String> paymentMethods = new ArrayList<>();
        Arrays.stream(Constants.PaymentMethod.values()).forEach(type->paymentMethods.add(type.name()));
        return paymentMethods;
    }
}
