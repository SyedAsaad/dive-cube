package com.demo.dive.cube.service;

import com.demo.dive.cube.dto.BookCourseDto;
import com.demo.dive.cube.model.BookCourse;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.repository.BookCourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookCourseService {

    @Autowired
    private BookCourseRepository bookCourseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassRoomService classRoomService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    public List<BookCourse> findAll(){ return bookCourseRepository.findAllByIsDeletedFalse(); }

    public BookCourse findOne(Long id){ return bookCourseRepository.findByIdAndIsDeletedFalse(id); }


    public ModelAndView addDependentDetails(ModelAndView modelAndView) {
        modelAndView.addObject("bookCourseList",findAll());
        modelAndView.addObject("students",studentService.findAllStudents());
        modelAndView.addObject("courses",courseService.findAll());
        modelAndView.addObject("classRooms",classRoomService.findAll());
        modelAndView.addObject("instructors",instructorService.findAll());
        return modelAndView;
    }

    public void saveCourseBooking(BookCourse bookCourse) {
        if(bookCourse != null){
            if(bookCourse.getId()!=null){
                BookCourse existCourseBooking=findOne(bookCourse.getId());
                if(existCourseBooking!=null)
                    bookCourseRepository.save(bookCourse);
            }
            else{
                bookCourseRepository.save(bookCourse);
            }
        }
    }

    public void deleteBooking(Long id){
        BookCourse existCourseBooking=findOne(id);
        if(existCourseBooking!=null)
            if(!existCourseBooking.getIsTransactionExist()){
                existCourseBooking.setIsDeleted(true);
                bookCourseRepository.save(existCourseBooking);
            }
    }

    public BookCourseDto getBookCourseDto(Long id) {
        BookCourseDto bookCourseDto = new BookCourseDto();
        BookCourse existCourseBook=findOne(id);
        if(existCourseBook!=null) {
            bookCourseDto.setCourse(existCourseBook.getCourse());
            bookCourseDto.setId(existCourseBook.getId());
            bookCourseDto.setClassRoom(existCourseBook.getClassRoom());
            bookCourseDto.setInstructor(existCourseBook.getInstructor());
            bookCourseDto.setCourseDate(existCourseBook.getCourseDate());
            bookCourseDto.setCourseTime(existCourseBook.getCourseTime());
            bookCourseDto.setStudentList(new ArrayList<Student>(){{add(existCourseBook.getStudent());}});
        }
         return bookCourseDto;
    }

    public void getBookCourseNdSave(BookCourseDto bookCourseDto) {
        try {
            if(bookCourseDto!=null){
                int i=0;
                while(bookCourseDto.getStudentList().size()>i){
                    BookCourse bookCourse = new BookCourse();
                    bookCourse.setCourse(bookCourseDto.getCourse());
                    bookCourse.setId(bookCourseDto.getId());
                    bookCourse.setClassRoom(bookCourseDto.getClassRoom());
                    bookCourse.setInstructor(bookCourseDto.getInstructor());
                    bookCourse.setCourseDate(bookCourseDto.getCourseDate());
                    bookCourse.setCourseTime(bookCourseDto.getCourseTime());
                    bookCourse.setStudent(bookCourseDto.getStudentList().get(i));
                    bookCourse.setBookingId(generateBookingId());
                    saveCourseBooking(bookCourse);
                    i++;
                }
            }
        }
        catch (Exception e){
        System.out.println(e.getStackTrace());
        }

    }

    private String generateBookingId() {
        Long id= bookCourseRepository.getHighestId();
        if(id!=null)
        return "BC-"+id;
        else
            return "BC-01";

    }

    public void updateCourseStatus(Long id) {
        BookCourse bookCourse = findOne(id);
        bookCourse.setIsCompleted(Boolean.TRUE);
        bookCourseRepository.save(bookCourse);
    }
}
