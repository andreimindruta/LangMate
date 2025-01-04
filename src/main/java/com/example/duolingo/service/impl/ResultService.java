package com.example.duolingo.service.impl;

import com.example.duolingo.controller.payload.request.SaveResultRequest;
import com.example.duolingo.controller.payload.response.GetResultResponse;
import com.example.duolingo.controller.payload.response.GetResultsResponse;
import com.example.duolingo.domain.entities.Result;
import com.example.duolingo.repository.LanguageRepository;
import com.example.duolingo.repository.ResultRepository;
import com.example.duolingo.validation.DuolingoRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {

    private final UserService userService;
    private final LanguageRepository languageRepository;
    private final ResultRepository resultRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    public GetResultResponse saveResult(final SaveResultRequest request)
            throws DuolingoRuntimeException {
        val currentUser = userService.getCurrentUser().orElseThrow(
                () -> new DuolingoRuntimeException(403, "User not found")
        );
        ;
        val language = languageRepository.findByName(request.languageName()).orElseThrow(()
                -> new DuolingoRuntimeException(404, "Language not found"));
        val result = Result.builder()
                .grade(request.grade())
                .user(currentUser)
                .language(language)
                .timestamp(new Date())
                .build();
        val savedResult = resultRepository.save(result);
        return new GetResultResponse(savedResult.getGrade(), savedResult.getLanguage().getName(),formatter.format(result.getTimestamp()));
    }

    public GetResultsResponse findResultsForLanguage(final String languageName)
            throws DuolingoRuntimeException {
        val language = languageRepository.findByName(languageName).orElseThrow(()
                -> new DuolingoRuntimeException(400, "Language not found"));
        val currentUser = userService.getCurrentUser().orElseThrow(
                () -> new DuolingoRuntimeException(403, "User not found")
        );
        val results = resultRepository.findAllByLanguageAndUserOrderByTimestamp(language, currentUser);
        if (results.isEmpty()) {
            throw new DuolingoRuntimeException(400, "No results found for user");
        }
        val mappedResults = results.stream()
                .map(result -> new GetResultResponse(result.getGrade(), result.getLanguage().getName(), formatter.format(result.getTimestamp())))
                .toList();
        return new GetResultsResponse(mappedResults);
    }

    public GetResultResponse getResultDetails(final Long resultId)
            throws DuolingoRuntimeException {
        val result = resultRepository.findById(resultId).orElseThrow(()
                -> new DuolingoRuntimeException(400, "No result found"));
        return new GetResultResponse(result.getGrade(), result.getLanguage().getName(), formatter.format(result.getTimestamp()));
    }
}
