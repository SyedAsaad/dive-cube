package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.BookDive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDiveRepository extends JpaRepository<BookDive,Long> {

    BookDive findByIdAndIsDeletedFalse(Long id);
    List<BookDive> findAllByIsDeletedFalse();

    List<BookDive> findAllByOrderById();
}
