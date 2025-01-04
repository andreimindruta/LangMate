package com.example.duolingo.service.impl;

import com.example.duolingo.controller.payload.response.GetLessonResponse;
import com.example.duolingo.controller.payload.response.GetLessonsResponse;
import com.example.duolingo.domain.entities.Lesson;
import com.example.duolingo.repository.LanguageRepository;
import com.example.duolingo.repository.LessonRepository;
import com.example.duolingo.validation.DuolingoRuntimeException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {

  private final UserService userService;
  private final LessonRepository lessonRepository;
  private final LanguageRepository languageRepository;

  public GetLessonsResponse getLessons(final String languageName) throws DuolingoRuntimeException {
      val currentUser = userService.getCurrentUser().orElseThrow(
          () -> new DuolingoRuntimeException(403,"User not found")
      );;
      val userLanguages = currentUser.getLanguages();
      if(userLanguages.isEmpty()){
        throw new DuolingoRuntimeException(404,"User not enrolled in any languages");
      }
      val language = languageRepository.findByName(languageName).orElseThrow(
          () -> new DuolingoRuntimeException(400,"Language does not exist")
      );
      if(!userLanguages.contains(language)){
        throw new DuolingoRuntimeException(400,"User not enrolled in this language");
      }
      val lessons = lessonRepository.findAllByLanguage(language)
          .stream()
          .map(lesson -> new GetLessonResponse(lesson.getTitle(),lesson.getText()))
          .toList();
      return new GetLessonsResponse(lessons);
  }

}
