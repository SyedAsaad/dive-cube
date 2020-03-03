package com.demo.dive.cube.config.exception;

import com.demo.dive.cube.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;

@ControllerAdvice
public class CustomExceptionHandler {

//    private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(
            {AuthenticationException.class, AuthenticationServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
//        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }



    @ExceptionHandler({DuplicateRecordException.class})
    protected ResponseEntity<ResponseDto> handleException(DuplicateRecordException dre) {
        return new ResponseEntity<>(new ResponseDto(dre.getMessage(), true), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({RecordNotFoundException.class})
    protected ResponseEntity<ResponseDto> handleException(RecordNotFoundException rnfe) {
        return new ResponseEntity<>(new ResponseDto(rnfe.getMessage(), true), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<ResponseDto> handleException(BadRequestException bre) {
        return new ResponseEntity<>(new ResponseDto(bre.getMessage(), true), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BaseException.class})
    protected ResponseEntity<ResponseDto> handleException(BaseException ae) {
        return new ResponseEntity<>(new ResponseDto(ae.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DbConnectionException.class})
    protected ResponseEntity<ResponseDto> handleException(DbConnectionException dbce) {
        return new ResponseEntity<>(new ResponseDto(dbce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<ResponseDto> handleException(ConstraintViolationException cve) {
        return new ResponseEntity<>(new ResponseDto<>(Arrays.asList(cve.getMessage().split(", ")), true), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({AuthenticationException.class})
//    public ResponseEntity<ResponseDto> handleIOException(HttpServletRequest request, Exception e) {
//        return new ResponseEntity<>(new ResponseDto(e.getMessage(), true), HttpStatus.UNAUTHORIZED);
//    }

}