package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.CountryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class CommonService {

    public List<CountryDto> getCountryData() {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();

            InputStream inputStream = Object.class.getResourceAsStream("/json/countries.json");
            CountryDto[] countryDtos =  jsonMapper.readValue(inputStream,CountryDto[].class);
            return  Arrays.asList(countryDtos);
        }
        catch (IOException e){
            return null;
        }
    }

}
