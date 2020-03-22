package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Constants;
import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.enums.PaymentType;
import com.demo.dive.cube.enums.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EnumService {

    public Map<Integer, String> findAllPaymentMethod(){
        return PaymentMethod.keyValues;
    }

    public Map<Integer, String> findAllPaymentType(){ return PaymentType.keyValues; }

}
