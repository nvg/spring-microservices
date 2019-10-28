package ca.psdev.mssc.productservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException e) {
        List<String> result = new ArrayList<>();

        e.getConstraintViolations().forEach(v -> {
            result.add(v.getPropertyPath() + ": " + e.getMessage());
        });

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
