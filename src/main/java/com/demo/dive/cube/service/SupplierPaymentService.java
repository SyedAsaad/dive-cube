package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.PaymentDto;
import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.model.SupplierPayment;
import com.demo.dive.cube.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierPaymentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentRepository paymentRepository;


    public void savePayment(PaymentDto paymentDto) {
        if(paymentDto!=null){
            SupplierPayment supplierPayment = new SupplierPayment();
            supplierPayment.setPaymentDate(paymentDto.getPaymentDate());
            supplierPayment.setAmount(paymentDto.getAmount());
            supplierPayment.setItem(paymentDto.getItemId());
            supplierPayment.setPaymentMethod(paymentDto.getPaymentMethod());
            supplierPayment.setPaymentType(paymentDto.getPaymentType());
            supplierPayment.setSupplier(paymentDto.getSupplierId());
            paymentRepository.save(supplierPayment);

        }
    }

    public List<SupplierPayment> findAll(){
        return paymentRepository.findAll();
    }

}
