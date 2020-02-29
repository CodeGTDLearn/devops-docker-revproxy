package webapp.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private Environment env;

    @ExceptionHandler(UsernameDuplicated.class)
    public ResponseEntity<?> UsernameDuplicated(UsernameDuplicated exception) {
        ResourceNotFoundDetails excDet = new ResourceNotFoundDetails(
                env.getProperty("title.UsernameDuplicated"),
                exception.getMessage(),
                exception.getClass().getName(),
                HttpStatus.BAD_REQUEST.value(),
                new Date().getTime());

        return new ResponseEntity<>(excDet, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> ResourceNotFoundException(ResourceNotFound exception) {
        ResourceNotFoundDetails excDet = new ResourceNotFoundDetails(
                env.getProperty("title.ResourceNotFound"),
                exception.getMessage(),
                exception.getClass().getName(),
                HttpStatus.NOT_FOUND.value(),
                new Date().getTime());

        return new ResponseEntity<>(excDet, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> ConstraintViolationException(DataIntegrityViolationException exception) {
        ResourceNotFoundDetails excDet = new ResourceNotFoundDetails(
                env.getProperty("title.ConstraintError"),
                exception.getMessage(),
                exception.getClass().getName(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date().getTime());

        return new ResponseEntity<>(excDet, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ResourceNotFoundDetails excDet = new ResourceNotFoundDetails(
                env.getProperty("title.HttpMessageNotReadable"),
                exception.getMessage(),
                exception.getClass().getName(),
                HttpStatus.BAD_REQUEST.value(),
                new Date().getTime());

        return new ResponseEntity<>(excDet, HttpStatus.BAD_REQUEST);

    }
}
