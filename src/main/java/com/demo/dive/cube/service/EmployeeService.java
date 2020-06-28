package com.demo.dive.cube.service;

import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.dto.EmployeeDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.enums.EmployementType;
import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.jrbeans.EmployeeJrBean;
import com.demo.dive.cube.model.Employee;
import com.demo.dive.cube.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    private ReportService reportService;

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

    public void employeeReport(HttpServletRequest request, HttpServletResponse response) {
        Map<Integer, Object> parameters = new LinkedHashMap<>();
        String empName = request.getParameter("name");
        List<Employee> data = new ArrayList<>();
        if(empName!=null && !empName.isEmpty())
            data =  employeeRepository.findAllByNameAndIsDeletedFalseOrderByStartDate(empName);
        else
            data = employeeRepository.findAllByIsDeletedFalseOrderByStartDate();


        reportService.export(getJrBean(data), ReportConstants.EMPLOYEE_REPORT, request, response);

    }

    private List<EmployeeJrBean> getJrBean(List<Employee> employeeList){
        List<EmployeeJrBean> employeeJrBeans = new ArrayList<>();
        for(Employee employee: employeeList){
            EmployeeJrBean employeeJrBean = new EmployeeJrBean();
            BeanUtils.copyProperties(employee,employeeJrBean);
            employeeJrBean.setEmployementType(employee.getEmployementType().getTitle());
            employeeJrBeans.add(employeeJrBean);
        }
        return employeeJrBeans;
    }
}
