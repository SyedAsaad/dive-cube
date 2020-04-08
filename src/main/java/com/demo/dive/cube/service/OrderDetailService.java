package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.model.OrderDetail;
import com.demo.dive.cube.repository.OrderDetailRepository;
import com.demo.dive.cube.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    public Long save(OrderDetail orderDetail,Long orderId){
        Order order = null;
        if(orderDetail != null){
            if(orderDetail.getId() == null){
                order = orderService.findOne(orderId);
                orderDetail.setOrder(order);
                orderDetailRepository.save(orderDetail);
//                order.getOrderDetailList().add(orderDetail);

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
        orderService.save(order);
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
            orderDetailRepository.delete(orderDetail);
            orderService.save(orderService.findOne(orderDetail.getOrder().getId()));
            return orderDetail.getOrder().getId();
        }
        return null;
    }

    public OrderDetail findOne(Long id){
        return orderDetailRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
