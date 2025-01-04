package com.example.duolingo.controller;


import com.example.duolingo.controller.payload.response.GetLessonsResponse;
import com.example.duolingo.service.impl.LessonService;
import com.example.duolingo.validation.DuolingoRuntimeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duolingo/lesson")
@Slf4j
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;

  @GetMapping(path = "/{languageName}")
  public ResponseEntity<GetLessonsResponse> getLessons(
      @PathVariable @NonNull String languageName) throws DuolingoRuntimeException {
      return ResponseEntity.ok(lessonService.getLessons(languageName));
  }

}
