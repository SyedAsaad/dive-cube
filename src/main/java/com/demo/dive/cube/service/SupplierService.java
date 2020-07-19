package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Queries;
import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.jrbeans.DetailOrderFormJrBean;
import com.demo.dive.cube.model.Supplier;
import com.demo.dive.cube.repository.SupplierRepository;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @PersistenceContext
    public EntityManager em;

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

    public void exportSalesReport(HttpServletRequest request, HttpServletResponse response){
        String criteria = "";
        int parameterNo = 1;
        Map<Integer,Object> parameters = new LinkedHashMap<>();

        if(request.getParameter("supplier") != null && !request.getParameter("supplier").toString().isEmpty()){
            criteria += " AND a.name = ?";
            parameters.put(parameterNo,request.getParameter("supplier").toString());
            parameterNo++;
        }
        if(request.getParameter("fromDate") != null && !request.getParameter("fromDate").toString().isEmpty() && request.getParameter("toDate") != null && !request.getParameter("toDate").toString().isEmpty()){
            criteria += " AND STR_TO_DATE(b.order_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')";
            parameters.put(parameterNo,request.getParameter("fromDate"));
            parameterNo++;
            criteria += " AND STR_TO_DATE(b.order_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')";
            parameters.put(parameterNo,request.getParameter("toDate"));
            parameterNo++;
        }

        Query query = em.createNativeQuery(Queries.salesQuery+criteria);
        List<Object> results = UtilService.toParameterized(query,parameters,parameterNo).getResultList();

        List<DetailOrderFormJrBean> data = new ArrayList<>();

        for(Object object: results){
            int i = 0;
            Object[] obj = (Object[])object;
            DetailOrderFormJrBean detailOrderFormJrBean = new DetailOrderFormJrBean();
            detailOrderFormJrBean.setName(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setOrderDate(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setItemId(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setItemName(UtilService.isValid(obj[i++]));
            detailOrderFormJrBean.setAmount((double)obj[i++]);
            detailOrderFormJrBean.setQuantity((int)obj[i++]);
            detailOrderFormJrBean.setTotal((double)obj[i++]);

            data.add(detailOrderFormJrBean);
        }

        reportService.export(data,ReportConstants.SALES_REPORT,request,response);
    }

}
