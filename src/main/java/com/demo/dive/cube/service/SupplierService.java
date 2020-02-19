package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Supplier;
import com.demo.dive.cube.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public void save(Supplier supplier){
        if(supplier != null){
            if(supplier.getId() == null){
                supplierRepository.save(supplier);
            }
            else{
                Supplier supplierExist = findOne(supplier.getId());
                if(supplierExist != null){
                    supplierExist = supplier;
                    supplierRepository.save(supplierExist);
                }
            }
        }
    }

    public List<Supplier> findAll(){
        return supplierRepository.findAll();
    }

    public void delete(Long id){
        Supplier supplier = findOne(id);
        if(supplier != null){
            supplierRepository.delete(supplier);
        }
    }

    public Supplier findOne(Long id){
        return supplierRepository.findById(id).get();
    }
}
