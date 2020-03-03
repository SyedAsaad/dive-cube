package com.demo.dive.cube.service;

import com.demo.dive.cube.model.DiveType;
import com.demo.dive.cube.repository.DiveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiveTypeService {
    @Autowired
    private DiveTypeRepository diveTypeRepository;

    public void save(DiveType diveType) {
        if(diveType != null ){
            if(diveType.getId()!=null){
                DiveType existDiveType = diveTypeRepository.findById(diveType.getId()).get();
                if(existDiveType != null){
                    diveTypeRepository.save(diveType);
                }
            }else
                diveTypeRepository.save(diveType);
        }
    }

    public void delete(Long id) {
        DiveType diveType=  findOne(id);
            if(diveType != null)
            {
                diveType.setIsDeleted(true);
                diveTypeRepository.save(diveType);
            }
    }

    public DiveType findOne(Long id) {
        if(id != null)
        return diveTypeRepository.findById(id).get();
        else return null;
    }

    public List<DiveType> findAll() {
        return diveTypeRepository.findAllByIsDeletedFalse();
    }
}
