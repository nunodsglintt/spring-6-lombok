package guru.springframework.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionController {

  //  @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFound(){
        return ResponseEntity.notFound().build();
    }

}
