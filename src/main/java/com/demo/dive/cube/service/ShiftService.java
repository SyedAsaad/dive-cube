package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Shift;
import com.demo.dive.cube.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    public Shift findOne(Long id){
        return shiftRepository.findByIdAndIsDeletedFalse(id);
    }

    public List<Shift> findAll(){
        return shiftRepository.findAllByIsDeletedFalse();

    }

    public void saveShift(Shift shift){
        Shift existShift = new Shift();
        if(shift.getId()!=null){
           existShift = findOne(shift.getId());
        }
        if(existShift!=null){
            shiftRepository.save(shift);
        }
    }

    public void deleteShift(Long id){
        Shift existShift=findOne(id);
        if(existShift!=null){
//            existShift.setIsDeleted(Boolean.TRUE);
            shiftRepository.delete(existShift);
        }
    }


}
