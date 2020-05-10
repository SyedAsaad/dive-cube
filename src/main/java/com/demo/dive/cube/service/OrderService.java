package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Constants;
import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.model.OrderDetail;
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

    @Autowired
    private OrderDetailService orderDetailService;

    public void save(Order order){
        if(order != null){
           Double totalAmount= order.getOrderDetailList().parallelStream().mapToDouble(orderDetail->orderDetail.getQuantity()*orderDetail.getAmount()).sum();
            if(order.getId() == null){
                order.setOrderId(generateOrderId());
                order.setAmount(totalAmount);
                orderRepository.save(order);
            }
            else{
                Order orderExist = findOne(order.getId());
                if(orderExist != null){
                    orderExist = order;
                    order.setAmount(totalAmount);
                    orderRepository.save(orderExist);
                }
            }
        }
    }

    private String generateOrderId() {
        String orderId;
        Long id= orderRepository.getHighestId();

            orderId = "SO-"+id;

            return orderId;
    }


    public List<Order> findAll(){
        List<Order> orderList = orderRepository.findAllByIsDeletedFalse() ;
//        orderList.stream().forEach(order -> order.setAmount(order.getOrderDetailList().parallelStream().mapToDouble(orderDetail->orderDetail.getQuantity()*orderDetail.getAmount()).sum()));
        return orderList;
    }

    public void delete(Long id){
        Order order = findOne(id);
        if(order != null){
            order.setIsDeleted(true);
//            order.getOrderDetailList().forEach(orderDetail -> orderDetailService.delete(orderDetail.getId()));
            orderRepository.save(order);
        }
    }

    public Order findOne(Long id){
        return orderRepository.findOneByIdAndIsDeletedFalse(id);
    }


}
