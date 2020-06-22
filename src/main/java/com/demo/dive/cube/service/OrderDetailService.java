package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Queries;
import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.jrbeans.DetailOrderFormJrBean;
import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.model.OrderDetail;
import com.demo.dive.cube.repository.OrderDetailRepository;
import com.demo.dive.cube.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReportService reportService;

    @PersistenceContext
    public EntityManager em;

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

    public void exportReport(HttpServletRequest request, HttpServletResponse response) {
        String criteria = "";
        int parameterNo = 1;
        Map<Integer,Object> parameters = new LinkedHashMap<>();
        String from = request.getParameter("fromDate");
        String to = request.getParameter("toDate");

        if(from!= null && !from.toString().isEmpty() && to != null && !to.toString().isEmpty()){
            criteria += " AND STR_TO_DATE(b.order_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')";
            parameters.put(parameterNo,from);
            parameterNo++;
            criteria += " AND STR_TO_DATE(b.order_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')";
            parameters.put(parameterNo,to);
            parameterNo++;
        }
        Query query = em.createNativeQuery(Queries.orderSummary.replace("criteria",criteria));

        List<Object> results = UtilService.toParameterized(query,parameters,parameterNo).getResultList();
        String fromTo = "From "+from+" To "+to;
        List<DetailOrderFormJrBean> data = new ArrayList<>();

        for(Object object: results){
            int i = 0;
            Object[] obj = (Object[])object;
            DetailOrderFormJrBean detailOrderFormJrBean = new DetailOrderFormJrBean();
            detailOrderFormJrBean.setCompany(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setTotalOrder(String.valueOf(obj[i++]));
            detailOrderFormJrBean.setAmount((double)obj[i++]);
            detailOrderFormJrBean.setOrderDate(fromTo);
            data.add(detailOrderFormJrBean);
        }

        reportService.export(data, ReportConstants.ORDER_SUMMARY_REPORT,request,response);
    }
}
