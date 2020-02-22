package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Constants;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EnumService {

    public Map<Integer, String> findAllPaymentMethod(){
        return Constants.PaymentMethod.keyValues;
    }

    public Map<Integer, String> findAllPaymentType(){
        return Constants.PaymentType.keyValues;
    }

}
