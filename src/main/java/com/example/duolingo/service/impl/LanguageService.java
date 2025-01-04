package com.example.duolingo.service.impl;

import com.example.duolingo.controller.payload.response.GetLanguageResponse;
import com.example.duolingo.controller.payload.response.GetLanguagesResponse;
import com.example.duolingo.repository.LanguageRepository;
import com.example.duolingo.repository.UserRepository;
import com.example.duolingo.validation.DuolingoRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LanguageService {

  private final LanguageRepository languageRepository;

  private final UserService userService;
  private final UserRepository userRepository;


  public GetLanguagesResponse getUserLanguages() throws DuolingoRuntimeException {
    val currentUser = userService.getCurrentUser()
        .orElseThrow(() -> new DuolingoRuntimeException(403,"Please login to access this feature"));
    val userLanguages = currentUser.getLanguages()
        .stream()
        .map(language -> new GetLanguageResponse(language.getName()))
        .toList();
    return new GetLanguagesResponse(userLanguages);
  }

  public GetLanguagesResponse getLanguages() {
    val languages = languageRepository.findAll()
        .stream()
        .map(l-> new GetLanguageResponse(l.getName()))
        .toList();
    return new GetLanguagesResponse(languages);
  }
  public GetLanguagesResponse addLanguageToUser(final String languageName) throws DuolingoRuntimeException {
    val currentUser = userService.getCurrentUser()
        .orElseThrow(() -> new DuolingoRuntimeException(403,"Please login to access this api"));
    val language = languageRepository
        .findByName(languageName)
        .orElseThrow(() -> new DuolingoRuntimeException(400, "Language does not exist"));
    val userLanguages = currentUser.getLanguages();
    userLanguages.add(language);
    currentUser.setLanguages(userLanguages);
    val updatedUser = userRepository.save(currentUser);
    val updatedUserLanguages = updatedUser.getLanguages()
        .stream()
        .map(l-> new GetLanguageResponse(l.getName()))
        .toList();
    return new GetLanguagesResponse(updatedUserLanguages);
  }

}
