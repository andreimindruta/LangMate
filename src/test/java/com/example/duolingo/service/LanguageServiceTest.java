package com.example.duolingo.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.example.duolingo.controller.payload.response.GetLanguageResponse;
import com.example.duolingo.domain.entities.Language;
import com.example.duolingo.domain.entities.Lesson;
import com.example.duolingo.domain.entities.Question;
import com.example.duolingo.domain.entities.Result;
import com.example.duolingo.domain.entities.User;
import com.example.duolingo.repository.LanguageRepository;
import com.example.duolingo.service.impl.LanguageService;
import com.example.duolingo.service.impl.UserService;
import com.example.duolingo.validation.DuolingoRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class LanguageServiceTest {


  public static final String MOCK_LANGUAGE = "MOCK_LANGUAGE";
  @Mock
  private LanguageRepository languageRepository;

  @Mock
  private UserService userService;

  @InjectMocks
  private LanguageService languageService;


  @Test
  void getLanguagesTest() {
    val languagesList = List.of(getOneLanguage().get());
    val expectedResponse = languagesList
        .stream().map(l -> new GetLanguageResponse(l.getName())).collect(
        Collectors.toList());
    when(languageRepository.findAll()).thenReturn(languagesList);
    val response = languageService.getLanguages();
    val actualResponse = response.languages();
    Assertions.assertIterableEquals(expectedResponse,actualResponse);
  }



  private Optional<Language> getOneLanguage(){
    val language = new Language();
    language.setName(MOCK_LANGUAGE);
    return Optional.of(language);
  }
  private Optional<User> getOneUser(){
    return Optional.of(new User());
  }

}
