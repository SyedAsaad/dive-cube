package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.PaymentDto;
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
        return paymentRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        SupplierPayment supplierPayment = paymentRepository.findById(id).get();
        if(supplierPayment != null){
            supplierPayment.setIsDeleted(Boolean.TRUE);
            paymentRepository.save(supplierPayment);
        }
    }

    public PaymentDto findOne(Long id){
        SupplierPayment supplierPayment = paymentRepository.findById(id).get();
        PaymentDto paymentDto = new PaymentDto();
        if(supplierPayment!=null){
            paymentDto.setAmount(supplierPayment.getAmount());
            paymentDto.setItemId(supplierPayment.getItem());
            paymentDto.setPaymentDate(supplierPayment.getPaymentDate());
            paymentDto.setPaymentMethod(supplierPayment.getPaymentMethod().ordinal());
            paymentDto.setPaymentType(supplierPayment.getPaymentType().ordinal());
            paymentDto.setSupplierId(supplierPayment.getSupplier());

        }
        return paymentDto;
    }
}
