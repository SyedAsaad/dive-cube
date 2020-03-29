package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.BookCourse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookCourseRepository extends CrudRepository<BookCourse,Long> {

    List<BookCourse> findAllByIsDeletedFalse();

    BookCourse findByIdAndIsDeletedFalse(Long id);
}
