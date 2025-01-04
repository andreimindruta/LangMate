package com.example.duolingo.aop;

import com.example.duolingo.controller.payload.response.DuolingoErrorResponse;
import com.example.duolingo.validation.DuolingoRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler({
      DuolingoRuntimeException.class
  })
  public ResponseEntity<DuolingoErrorResponse> hanldeExceptions(DuolingoRuntimeException e){
      return ResponseEntity.status(e.getStatus()).body(new DuolingoErrorResponse(e.getMessage()));
  }

  @ExceptionHandler({
      NullPointerException.class
  })
  public ResponseEntity<DuolingoErrorResponse> hanldeNullExceptions(NullPointerException e){
    return ResponseEntity.status(500).body(new DuolingoErrorResponse(e.getMessage()));
  }


}
