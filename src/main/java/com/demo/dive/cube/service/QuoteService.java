package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Quote;
import com.demo.dive.cube.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepository;

    public void save(Quote quote){
        if(quote != null){
            if(quote.getId() == null){
                quoteRepository.save(quote);
            }
            else{
                Quote quoteExist = findOne(quote.getId());
                if(quoteExist != null){
                    quoteExist = quote;
                    quoteRepository.save(quoteExist);
                }
            }
        }
    }

    public List<Quote> findAll(){
        return quoteRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Quote quote = findOne(id);
        if(quote != null){
            quote.setIsDeleted(true);
            quoteRepository.save(quote);
        }
    }

    public Quote findOne(Long id){
        return quoteRepository.findOneByIdAndIsDeletedFalse(id);
    }
}
