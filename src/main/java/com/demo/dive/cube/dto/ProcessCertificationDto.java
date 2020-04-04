package com.demo.dive.cube.dto;

import com.demo.dive.cube.enums.CertificationType;
import com.demo.dive.cube.model.Student;

public class ProcessCertificationDto {

    private String bookingId;

    private Long id;

    private String dateOfCertificate;

    private String dateOfProcessing;

    private CertificationType certificationType;

    private String studentName;

    private String courseName;

    public ProcessCertificationDto() {
    }

    public ProcessCertificationDto(String bookingId, String studentName, String courseName) {
        this.bookingId = bookingId;
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public ProcessCertificationDto(String bookingId, Long id, String dateOfCertificate, String dateOfProcessing, CertificationType certificationType, String studentName, String courseName) {
        this.bookingId = bookingId;
        this.id = id;
        this.dateOfCertificate = dateOfCertificate;
        this.dateOfProcessing = dateOfProcessing;
        this.certificationType = certificationType;
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public String getDateOfCertificate() {
        return dateOfCertificate;
    }

    public void setDateOfCertificate(String dateOfCertificate) {
        this.dateOfCertificate = dateOfCertificate;
    }

    public String getDateOfProcessing() {
        return dateOfProcessing;
    }

    public void setDateOfProcessing(String dateOfProcessing) {
        this.dateOfProcessing = dateOfProcessing;
    }

    public CertificationType getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
