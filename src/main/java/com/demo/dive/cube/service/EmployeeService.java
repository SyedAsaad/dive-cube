package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.EmployeeDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.enums.EmployementType;
import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.model.Employee;
import com.demo.dive.cube.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private UserService userService;

    public void save(EmployeeDto employeeDto){

        Employee employeeExist = new Employee();
        if(employeeDto != null){
            UserDto userDto = getUserDto(employeeDto);
            if(employeeDto.getId() != null){
                 employeeExist = findOne(employeeDto.getId());
                 userDto.setId(userService.findUserByUsername(employeeExist.getEmail()).getId());
            }
            if(employeeExist != null && userDto!=null){
                    BeanUtils.copyProperties(employeeDto,employeeExist);
                    userService.saveNupdateUser(userDto, UserType.EMPLOYEE);
                    employeeRepository.save(employeeExist);
            }

        }
    }

    private UserDto getUserDto(EmployeeDto employeeDto){
        UserDto userDto =  new UserDto();
        userDto.setName(employeeDto.getName());
        userDto.setPassword(employeeDto.getPassword());
        userDto.setPhoneNumber(employeeDto.getTelephone());
        userDto.setEmail(employeeDto.getEmail());
        userDto.setAddress(employeeDto.getAddress());
        userDto.setShift(employeeDto.getShift());
        return userDto;
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
            employee.setIsDeleted(true);
            userService.deleteUser(userService.findUserByUsername(employee.getEmail()).getId());
            employeeRepository.save(employee);

        }
    }

    public Employee findOne(Long id){
        return employeeRepository.findOneByIdAndIsDeletedFalse(id);
    }
    public EmployeeDto getEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setShift(userService.findUserByUsername(employee.getEmail()).getShift());
        BeanUtils.copyProperties(employee,employeeDto);
        return employeeDto;
    }
}
