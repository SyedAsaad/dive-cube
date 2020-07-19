package com.demo.dive.cube.service;

import antlr.RecognitionException;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.config.exception.RecordNotFoundException;
import com.demo.dive.cube.config.exception.ServiceException;
import com.demo.dive.cube.dto.BookCourseDto;
import com.demo.dive.cube.enums.CourseStatus;
import com.demo.dive.cube.model.BookCourse;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.repository.BookCourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ProcessCertificateService processCertificateService;

    @Value("${book.course.file.path}")
    private String fileLocation;

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

    public void saveCourseBooking(BookCourseDto bookCourseDto, MultipartFile file) {
        BookCourse existCourseBooking = new BookCourse();
        if(bookCourseDto != null && file!=null){
            if(bookCourseDto.getId()!=null) {
                 existCourseBooking = findOne(bookCourseDto.getId());
            }
            if (existCourseBooking != null && checkPreviousCourseStatus(bookCourseDto.getStudent()).equals(CourseStatus.COMPLETED)) {
                if (!file.getOriginalFilename().isEmpty()) {
                    String fileName = existCourseBooking.getFileName() != null ? existCourseBooking.getFileName() : System.currentTimeMillis() + "-" + file.getOriginalFilename();
                    String filePath = uploadFile(file, fileName);
                    existCourseBooking.setFileName(filePath);
                }
                existCourseBooking.setBookingId(generateBookingId());
                BeanUtils.copyProperties(bookCourseDto, existCourseBooking);
                bookCourseRepository.save(existCourseBooking);
            }
        }
    }

    public void deleteBooking(Long id){
        BookCourse existCourseBooking=findOne(id);
        if(existCourseBooking!=null)
            if(!existCourseBooking.getIsTransactionExist()){
                existCourseBooking.setIsDeleted(true);
                bookCourseRepository.save(existCourseBooking);
                processCertificateService.deleteCertificateProcess(processCertificateService.findByBookCourse(existCourseBooking.getId()).getId());

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
            bookCourseDto.setStudent(existCourseBook.getStudent());
        }
         return bookCourseDto;
    }

//    public void getBookCourseNdSave(BookCourseDto bookCourseDto) {
//        try {
//            if(bookCourseDto!=null){
//                int i=0;
//                while(bookCourseDto.getStudentList().size()>i){
//                    if(checkPreviousCourseStatus(bookCourseDto.getStudentList().get(i)).equals(CourseStatus.COMPLETED)){
//                        BookCourse bookCourse = new BookCourse();
//                        bookCourse.setCourse(bookCourseDto.getCourse());
//                        bookCourse.setId(bookCourseDto.getId());
//                        bookCourse.setClassRoom(bookCourseDto.getClassRoom());
//                        bookCourse.setInstructor(bookCourseDto.getInstructor());
//                        bookCourse.setCourseDate(bookCourseDto.getCourseDate());
//                        bookCourse.setCourseTime(bookCourseDto.getCourseTime());
//                        bookCourse.setStudent(bookCourseDto.getStudentList().get(i));
//                        bookCourse.setBookingId(generateBookingId());
//                        saveCourseBooking(bookCourse,bookCourseDto.getClaimFile());
//                    }
//                    i++;
//                }
//            }
//        }
//        catch (Exception e){
//        System.out.println(e.getStackTrace());
//        }
//
//    }

    private CourseStatus checkPreviousCourseStatus(Student student) {
        if(student.getId()!=null){
            BookCourse bookCourse = bookCourseRepository.findByIsCompletedFalseAndStudent_Id(student.getId());
            if(bookCourse==null)
                 return CourseStatus.COMPLETED;
            else
                return CourseStatus.IN_PROGRESS;
        }
        else{
            throw new RecordNotFoundException("Student not found");
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
        bookCourse.setCourseCompletionDate(formatter.format(new Date()));
        bookCourseRepository.save(bookCourse);
    }

    /**
     * upload student image to server
     * @param file
     * @param fileName
     * @return
     */
    private String uploadFile(MultipartFile file, String fileName){
        String fullFileName = fileName ;
        Boolean isFileUploaded = UtilService.uploadFile(file, fileLocation, fullFileName);
        if(!isFileUploaded) throw new BadRequestException("file.upload.error");
        return fullFileName;
    }

    public String getFileNameById(Long id) {
        return findOne(id).getFileName();
    }
}
