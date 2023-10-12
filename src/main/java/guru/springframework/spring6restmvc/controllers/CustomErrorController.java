package guru.springframework.spring6restmvc.controllers;

import jakarta.transaction.Transaction;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception){
        ResponseEntity.BodyBuilder response = ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException ve = (ConstraintViolationException) exception.getCause().getCause();

            List errors = ve.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        HashMap<String , String> errMap = new HashMap<>();
                        errMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage().toString());
                        return errMap;
                    }).collect(Collectors.toList());

            return response.body(errors);
        }
        return response.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleValidationErrors(MethodArgumentNotValidException exception){

        List errorList = exception.getFieldErrors().stream()
                .map(err ->{
                    HashMap<String, String> errorMap = new HashMap<>();
                    errorMap.put(err.getField(), err.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorList);
    }
}
