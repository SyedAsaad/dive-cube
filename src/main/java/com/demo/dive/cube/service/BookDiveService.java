package com.demo.dive.cube.service;

import com.demo.dive.cube.model.BookDive;
import com.demo.dive.cube.model.DiveType;
import com.demo.dive.cube.repository.BookDiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDiveService {

    @Autowired
    private BookDiveRepository bookDiveRepository;

    public BookDive findOne(Long id) {
        if(id != null)
            return bookDiveRepository.findById(id).get();
        else return null;
    }

    public List<BookDive> findAll() {
        return bookDiveRepository.findAllByIsDeletedFalse();
    }

    public void save(BookDive bookDive) {
        if(bookDive != null ){
            if(bookDive.getId()!=null){
                BookDive existBookDive = bookDiveRepository.findById(bookDive.getId()).get();
                if(existBookDive != null){
                    bookDiveRepository.save(bookDive);
                }
            }else
                bookDiveRepository.save(bookDive);
        }
    }

    public void delete(Long id) {
        BookDive bookDive=  findOne(id);
        if(bookDive != null)
        {
            bookDive.setIsDeleted(true);
            bookDiveRepository.save(bookDive);
        }
    }

}
