package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findOneByIdAndIsDeletedFalse(Long id);
    List<Employee> findAllByIsDeletedFalse();
    List<Employee> findAllByNameAndIsDeletedFalseOrderByStartDate(String name);
    List<Employee> findAllByIsDeletedFalseOrderByStartDate();
}
