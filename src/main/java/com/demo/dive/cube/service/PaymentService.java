package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.PaymentDto;
import com.demo.dive.cube.model.Payment;
import com.demo.dive.cube.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookCourseService bookCourseService;

    @Autowired
    private OrderService orderService;


    public String savePayment(PaymentDto paymentDto) {
        if (paymentDto != null) {
            Payment payment = new Payment();
            if (paymentDto.getId() != null) {
                payment = paymentRepository.findById(paymentDto.getId()).get();
            }
                if (payment != null) {
                    getPaymentObject(paymentDto, payment);
                    if(validateData(payment))
                    paymentRepository.save(payment);
                    return payment.getOrderId();
                }else {
                    return null;
                }

        }
        else {
            return null;
        }
    }

    private Boolean validateData(Payment payment) {
        String orderId = payment.getOrderId();
        String[] orderArray = orderId.split("-");
        Boolean isValid = false;
        Double totalPaymentDone=paymentRepository.getTotalSumByOrderId(orderId);
        if(orderArray[0].equalsIgnoreCase("BC")){
            Double coursePrice = bookCourseService.findOne(Long.parseLong(orderArray[1])).getCourse().getCoursePrice();
            if(coursePrice >= (totalPaymentDone!=null ? totalPaymentDone:0.0) + payment.getAmount())
                isValid = true;
        }
        else {
            Double orderAmount=orderService.findOne(Long.parseLong(orderArray[1])).getAmount();
            if(orderAmount >= (totalPaymentDone!=null ? totalPaymentDone:0.0) + payment.getAmount())
                isValid = true;
        }
        return isValid;
    }

    private void getPaymentObject(PaymentDto paymentDto, Payment payment) {
        if(paymentDto.getId()!=null) payment.setId(paymentDto.getId());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setAmount(paymentDto.getAmount());
        payment.setOrderId(paymentDto.getOrderId());
        payment.setPaymentMethod(paymentDto.getPaymentMethod().ordinal());
        payment.setPaymentType(paymentDto.getPaymentType().ordinal());
    }

    public List<Payment> findAll(){
        return paymentRepository.findAllByIsDeletedFalse();
    }
    public List<Payment> findAll(String orderId){
        return paymentRepository.findAllByOrderIdStartingWith(orderId.split("-")[0]);
    }

    public String delete(Long id){
        Payment payment = paymentRepository.findById(id).get();
        if(payment != null){
            payment.setIsDeleted(Boolean.TRUE);
            paymentRepository.save(payment);
            String orderType= payment.getOrderId().split("-")[0];
            if(orderType.equalsIgnoreCase("BC"))
                return "book/course";
            else return "order";
        }else return null;
    }

    public PaymentDto findOne(Long id){
        Payment payment = paymentRepository.findById(id).get();
        PaymentDto paymentDto = new PaymentDto();
        if(payment !=null){
            paymentDto.setId(payment.getId());
            paymentDto.setAmount(payment.getAmount());
            paymentDto.setOrderId(payment.getOrderId());
            paymentDto.setPaymentDate(payment.getPaymentDate());
            paymentDto.setPaymentMethod(payment.getPaymentMethod());
            paymentDto.setPaymentType(payment.getPaymentType());
        }
        return paymentDto;
    }

}
