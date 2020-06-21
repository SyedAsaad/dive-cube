package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Constants;
import com.demo.dive.cube.config.Queries;
import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.jrbeans.DetailOrderFormJrBean;
import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.model.OrderDetail;
import com.demo.dive.cube.model.Supplier;
import com.demo.dive.cube.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @PersistenceContext
    public EntityManager em;

    @Autowired
    private ReportService reportService;

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
                    orderExist.setAmount(totalAmount);
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

    public void exportReport(HttpServletRequest request, HttpServletResponse response) {
        String criteria = "";
        int parameterNo = 1;
        Map<Integer,Object> parameters = new LinkedHashMap<>();

        if(request.getParameter("company") != null && !request.getParameter("company").toString().isEmpty()){
            criteria += " AND a.company = ?";
            parameters.put(parameterNo,request.getParameter("company").toString());
            parameterNo++;
        }
        if(request.getParameter("from") != null && !request.getParameter("from").toString().isEmpty() && request.getParameter("to") != null && !request.getParameter("to").toString().isEmpty()){
            criteria += "AND STR_TO_DATE(b.order_date,'%d-%m-%Y') >= ?";
            parameters.put(parameterNo,"STR_TO_DATE("+request.getParameter("from").toString()+",'%d-%m-%Y')");
            parameterNo++;
            criteria += "AND b.order_date <= ?";
            parameters.put(parameterNo,"STR_TO_DATE("+request.getParameter("to").toString()+",'%d-%m-%Y')");
            parameterNo++;
        }
        System.out.println(Queries.orderDetailQuery+criteria);
        Query query = em.createNativeQuery(Queries.orderDetailQuery+criteria);
        List<Object> results = UtilService.toParameterized(query,parameters,parameterNo).getResultList();

        List<DetailOrderFormJrBean> data = new ArrayList<>();

        for(Object object: results){
            int i = 0;
            Object[] obj = (Object[])object;
            DetailOrderFormJrBean detailOrderFormJrBean = new DetailOrderFormJrBean();
            detailOrderFormJrBean.setCompany(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setName(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setOrderDate(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setTrackingNum(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setItemId(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setItemName(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setAmount((double)obj[i++]);
            detailOrderFormJrBean.setQuantity((int)obj[i++]);
            detailOrderFormJrBean.setTotal((double)obj[i++]);

            data.add(detailOrderFormJrBean);
        }

        reportService.export(data,ReportConstants.DETAIL_ORDER_REPORT,request,response);
    }
}
