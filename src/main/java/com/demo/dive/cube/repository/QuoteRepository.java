package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote,Long> {
    public Quote findOneByIdAndIsDeletedFalse(Long id);
    public List<Quote> findAllByIsDeletedFalse();
}
