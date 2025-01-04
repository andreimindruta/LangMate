package com.example.duolingo.service.impl;

import com.example.duolingo.controller.payload.request.GetQuestionRequest;
import com.example.duolingo.controller.payload.request.PostAnswerRequest;
import com.example.duolingo.controller.payload.request.PostTestResultRequest;
import com.example.duolingo.controller.payload.response.GetQuestionResponse;
import com.example.duolingo.controller.payload.response.GetQuestionsResponse;
import com.example.duolingo.controller.payload.response.GetResultResponse;
import com.example.duolingo.domain.entities.Result;
import com.example.duolingo.repository.LanguageRepository;
import com.example.duolingo.repository.QuestionRepository;
import com.example.duolingo.repository.ResultRepository;
import com.example.duolingo.validation.DuolingoRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final QuestionRepository questionRepository;

    private final LanguageRepository languageRepository;

    private final UserService userService;

    private final ResultRepository resultRepository;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    public GetQuestionsResponse findQuestionsForTest(final GetQuestionRequest request)
            throws DuolingoRuntimeException {
        val language = languageRepository.findByName(request.languageName()).orElseThrow(
                () -> new DuolingoRuntimeException(404, "Language not found")
        );
        val questions = questionRepository.findAllByLanguage(language);

        Collections.shuffle(questions);
        val reducedListOfQuestions = questions.subList(0, 5);
        val mappedQuestions = reducedListOfQuestions
                .stream()
                .map(question -> new GetQuestionResponse(question.getQuestion()))
                .toList();
        return new GetQuestionsResponse(mappedQuestions);
    }

    public GetResultResponse saveResultForTest(final PostTestResultRequest request)
            throws DuolingoRuntimeException {

        val points = request.answers().stream()
                .map(this::mapQuestionAndAnswerToPoint)
                .toList();

        val language = languageRepository.findByName(request.languageName()).orElseThrow(
                () -> new DuolingoRuntimeException(400, "Language does not exist"));
        val grade = points.stream().mapToDouble(Integer::doubleValue).sum();
        val currentUser = userService.getCurrentUser();
        val result = Result.builder()
                .user(currentUser.get())
                .timestamp(new Date())
                .grade(grade)
                .language(language).build();
        val savedResult = resultRepository.save(result);
        return new GetResultResponse(savedResult.getGrade(), savedResult.getLanguage().getName(), formatter.format(result.getTimestamp()));
    }

    private Integer mapQuestionAndAnswerToPoint(PostAnswerRequest request) {
        val question = questionRepository.findByQuestion(request.question());
        if (question.getAnswer().equals(request.answer())) {
            return 1;
        }
        return 0;
    }

}
