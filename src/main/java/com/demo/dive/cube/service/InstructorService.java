package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.EmployeeDto;
import com.demo.dive.cube.dto.InstructorDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.model.Employee;
import com.demo.dive.cube.model.Instructor;
import com.demo.dive.cube.model.User;
import com.demo.dive.cube.repository.InstructorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserService userService;

    public void save(InstructorDto instructorDto){
        Instructor instructorExist = new Instructor();
        if(instructorDto != null){
            if(instructorDto.getId() == null){
                UserDto userDto = getUserDto(instructorDto);
                userService.saveNupdateUser(userDto, UserType.INSTRUCTOR);
                instructorExist.setEmail(instructorDto.getEmail());
                instructorExist.setInstructorName(instructorDto.getInstructorName());
                instructorRepository.save(instructorExist);
            }
            else{
                 instructorExist = findOne(instructorDto.getId());
                if(instructorExist != null){
                    UserDto userDto = getUserDto(instructorDto);
                    userDto.setId(userService.findUserByUsername(instructorExist.getEmail()).getId());
                    userService.saveNupdateUser(userDto, UserType.INSTRUCTOR);
                    instructorExist.setEmail(instructorDto.getEmail());
                    instructorExist.setInstructorName(instructorDto.getInstructorName()) ;
                    instructorRepository.save(instructorExist);
                }
            }
        }
    }

    private UserDto getUserDto(InstructorDto instructorDto){
        UserDto userDto =  new UserDto();
        userDto.setName(instructorDto.getInstructorName());
        userDto.setPassword(instructorDto.getPassword());
        userDto.setPhoneNumber(instructorDto.getPhoneNumber());
        userDto.setEmail(instructorDto.getEmail());
        userDto.setAddress(instructorDto.getAddress());
        userDto.setShift(instructorDto.getShift());
        return userDto;
    }

    public List<Instructor> findAll(){
        return instructorRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Instructor instructor = findOne(id);
        if(instructor != null){
            instructor.setIsDeleted(true);
            instructorRepository.save(instructor);
        }
    }

    public Instructor findOne(Long id){
        return instructorRepository.findOneByIdAndIsDeletedFalse(id);
    }

    public InstructorDto getInstructorDto(Instructor instructor){
        InstructorDto instructorDto = new InstructorDto();
        User user = userService.findUserByUsername(instructor.getEmail());
        instructorDto.setAddress(user.getAddress());
        instructorDto.setPhoneNumber(user.getPhoneNumber());
        instructorDto.setAddress(user.getAddress());
        BeanUtils.copyProperties(instructor,instructorDto);
        return instructorDto;
    }
}
