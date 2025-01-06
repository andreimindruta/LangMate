package com.example.langmate.service.impl;

import com.example.langmate.controller.payload.request.SaveResultRequest;
import com.example.langmate.controller.payload.response.GetResultResponse;
import com.example.langmate.controller.payload.response.GetResultsResponse;
import com.example.langmate.domain.entities.Result;
import com.example.langmate.repository.LanguageRepository;
import com.example.langmate.repository.ResultRepository;
import com.example.langmate.validation.LangmateRuntimeException;
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
    private final MilestoneService milestoneService;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    public GetResultsResponse findResultsForLanguage(final String languageName)
            throws LangmateRuntimeException {
        log.info("Fetching results for language: {}", languageName);
        val language = languageRepository.findByName(languageName).orElseThrow(()
                -> new LangmateRuntimeException(400, "Language not found"));
        val currentUser = userService.getCurrentUser().orElseThrow(
                () -> new LangmateRuntimeException(403, "User not found")
        );
        val results = resultRepository.findAllByLanguageAndUserOrderByTimestamp(language, currentUser);
        if (results.isEmpty()) {
            log.warn("No results found for userId: {} and language: {}", currentUser.getId(), languageName);
            throw new LangmateRuntimeException(400, "No results found for user");
        }
        val mappedResults = results.stream()
                .map(result -> new GetResultResponse(result.getGrade(), result.getLanguage().getName(),
                        formatter.format(result.getTimestamp())))
                .toList();
        log.info("Fetched {} results for userId: {} and language: {}", mappedResults.size(),
                currentUser.getId(), languageName);
        return new GetResultsResponse(mappedResults);
    }

    public GetResultResponse getResultDetails(final Long resultId)
            throws LangmateRuntimeException {
        log.info("Fetching result details for resultId: {}", resultId);
        val result = resultRepository.findById(resultId).orElseThrow(()
                -> new LangmateRuntimeException(400, "No result found"));
        log.info("Result found: {}", result);
        return new GetResultResponse(result.getGrade(), result.getLanguage().getName(),
                formatter.format(result.getTimestamp()));
    }

    public Double getAverageGradeForUserAndLanguage(Long userId, Long languageId) {
        log.info("Fetching average grade for userId: {}, languageId: {}", userId, languageId);
        Double averageGrade = resultRepository.findAverageGradeByUserAndLanguage(userId, languageId);
        log.info("Average grade fetched: {}", averageGrade);
        return averageGrade;
    }

    public int getTestsCountForUserAndLanguage(Long userId, Long languageId) {
        log.info("Fetching test count for userId: {}, languageId: {}", userId, languageId);
        int testCount = resultRepository.countTestsForUserAndLanguage(userId, languageId);
        log.info("Test count fetched: {}", testCount);
        return testCount;
    }
}
