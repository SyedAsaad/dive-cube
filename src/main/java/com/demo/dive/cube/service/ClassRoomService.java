package com.demo.dive.cube.service;

import com.demo.dive.cube.enums.ClassSection;
import com.demo.dive.cube.model.ClassRoom;
import com.demo.dive.cube.repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClassRoomService {
    @Autowired
    private ClassRoomRepository classRoomRepository;

    public void save(ClassRoom classRoom){
        if(classRoom != null){
            if(classRoom.getId() == null){
                classRoomRepository.save(classRoom);
            }
            else{
                ClassRoom classRoomExist = findOne(classRoom.getId());
                if(classRoomExist != null){
                    classRoomExist = classRoom;
                    classRoomRepository.save(classRoomExist);
                }
            }
        }
    }

    public List<String> getAllSections(){
        List<String> sections = new ArrayList<>();
        Arrays.stream(ClassSection.values()).forEach(type->sections.add(type.name()));
        return sections;
    }

    public List<ClassRoom> findAll(){
        return classRoomRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        ClassRoom classRoom = findOne(id);
        if(classRoom != null){
            classRoom.setIsDeleted(true);
            classRoomRepository.save(classRoom);
        }
    }

    public ClassRoom findOne(Long id){
        return classRoomRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
