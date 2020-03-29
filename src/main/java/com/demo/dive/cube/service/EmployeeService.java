package com.demo.dive.cube.service;

import com.demo.dive.cube.enums.EmployementType;
import com.demo.dive.cube.model.Employee;
import com.demo.dive.cube.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void save(Employee employee){
        if(employee != null){
            if(employee.getId() == null){
                employeeRepository.save(employee);
            }
            else{
                Employee employeeExist = findOne(employee.getId());
                if(employeeExist != null){
                    employeeExist = employee;
                    employeeRepository.save(employeeExist);
                }
            }
        }
    }

    public List<String> getAllEmploymentType(){
        List<String> employmentTypes = new ArrayList<>();
        Arrays.stream(EmployementType.values()).forEach(type->employmentTypes.add(type.name()));
        return employmentTypes;
    }

    public List<Employee> findAll(){
        return employeeRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Employee employee = findOne(id);
        if(employee != null){
            employee.setDeleted(true);
            employeeRepository.save(employee);
        }
    }

    public Employee findOne(Long id){
        return employeeRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
