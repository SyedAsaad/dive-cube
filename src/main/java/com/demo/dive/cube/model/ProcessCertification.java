package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.CertificationLevel;
import com.demo.dive.cube.enums.CertificationType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "process_certificate")
public class ProcessCertification extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="book_course_id", unique= true, nullable=false, insertable=true, updatable=true)
    private BookCourse bookCourse;

    private String dateOfCertificate;

    private String dateOfProcessing;

    private CertificationType certificationType;

    private CertificationLevel certificationLevel;

    public BookCourse getBookCourse() {
        return bookCourse;
    }

    public void setBookCourse(BookCourse bookCourse) {
        this.bookCourse = bookCourse;
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

    public void setCertificationType(Integer i) {
        this.certificationType = CertificationType.values()[i];
    }

    public CertificationLevel getCertificationLevel() {
        return certificationLevel;
    }

    public void setCertificationLevel(Integer i) {
        this.certificationLevel = CertificationLevel.values()[i];
    }
}
