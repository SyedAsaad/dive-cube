package com.demo.dive.cube.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EnumService<E extends Enum<E>> {

    public List<String> getEnumList(Enum<E>[] values) {
        List<String> enumList = new ArrayList<>();
        Arrays.stream(values).forEach(type->enumList.add(type.name()));
        return enumList;
    }
}
