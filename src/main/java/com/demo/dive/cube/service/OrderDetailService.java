package com.demo.dive.cube.service;

import com.demo.dive.cube.model.OrderDetail;
import com.demo.dive.cube.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void save(OrderDetail orderDetail){
        if(orderDetail != null){
            if(orderDetail.getId() == null){
                orderDetailRepository.save(orderDetail);
            }
            else{
                OrderDetail orderDetailExist = findOne(orderDetail.getId());
                if(orderDetailExist != null){
                    orderDetailExist = orderDetail;
                    orderDetailRepository.save(orderDetailExist);
                }
            }
        }
    }

    public List<OrderDetail> findAll(){
        return orderDetailRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        OrderDetail orderDetail = findOne(id);
        if(orderDetail != null){
            orderDetail.setIsDeleted(true);
            orderDetailRepository.save(orderDetail);
        }
    }

    public OrderDetail findOne(Long id){
        return orderDetailRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
