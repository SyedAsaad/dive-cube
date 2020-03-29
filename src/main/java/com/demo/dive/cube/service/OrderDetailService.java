package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.model.OrderDetail;
import com.demo.dive.cube.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    public Long save(OrderDetail orderDetail,Long orderId){
        Order order = null;
        if(orderDetail != null){
            if(orderDetail.getId() == null){
                order = orderService.findOne(orderId);
                orderDetail.setOrder(order);
                orderDetailRepository.save(orderDetail);
            }
            else{
                OrderDetail orderDetailExist = findOne(orderDetail.getId());
                if(orderDetailExist != null){
                    orderDetailExist = orderDetail;
                    order = orderService.findOne(orderDetailExist.getOrder().getId());
                    orderDetailExist.setOrder(order);
                    orderDetailRepository.save(orderDetailExist);
                }
            }
        }

        return order.getId();
    }

    public List<OrderDetail> findAll(){
        return orderDetailRepository.findAllByIsDeletedFalse();
    }

    public List<OrderDetail> findAllByOrderId(Long orderId){
        Order order = orderService.findOne(orderId);
        return orderDetailRepository.findAllByOrderAndIsDeletedFalse(order);
    }

    public Order getOrderByDetailId(Long orderId){
        return findOne(orderId).getOrder();
    }

    public Long delete(Long id){
        OrderDetail orderDetail = findOne(id);
        if(orderDetail != null){
            orderDetail.setIsDeleted(true);
            orderDetailRepository.save(orderDetail);
            return orderDetail.getOrder().getId();
        }
        return null;
    }

    public OrderDetail findOne(Long id){
        return orderDetailRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
