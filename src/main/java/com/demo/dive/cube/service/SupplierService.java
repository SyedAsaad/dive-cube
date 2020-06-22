package com.demo.dive.cube.service;

import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.model.Supplier;
import com.demo.dive.cube.repository.SupplierRepository;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ReportService reportService;

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
        return supplierRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Supplier supplier = findOne(id);
        if(supplier != null){
            supplier.setIsDeleted(true);
            supplierRepository.save(supplier);
        }
    }

    public Supplier findOne(Long id){
        return supplierRepository.findOneByIdAndIsDeletedFalse(id);
    }

    public void exportReport(Supplier supplier, HttpServletRequest request, HttpServletResponse response){
        if(supplier != null){
            if(supplier.getCompany() != null && !supplier.getCompany().isEmpty()){
                List<Supplier> suppliers = supplierRepository.findAllByCompanyAndIsDeletedFalse(supplier.getCompany());
                reportService.export(suppliers, ReportConstants.SUPPLIER_REPORT,request,response);
            }
            else{
                List<Supplier> suppliers = findAll();
                reportService.export(suppliers, ReportConstants.SUPPLIER_REPORT,request,response);
            }
        }
    }
}
