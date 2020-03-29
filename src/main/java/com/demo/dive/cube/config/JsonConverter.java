package com.demo.dive.cube.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class JsonConverter<T> {

    public T mapData(String object, Class<T> classType){
        T realDto;

        Gson gson = new Gson();
        realDto = gson.fromJson(String.valueOf(object), classType);

        return realDto;
    }



    public List<T> mapList(String jsonArray, Class<T> classType) {
        Type typeOfT = TypeToken.getParameterized(List.class, classType).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }
}
