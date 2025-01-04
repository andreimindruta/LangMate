package com.example.duolingo.controller;

import com.example.duolingo.controller.payload.request.SaveResultRequest;
import com.example.duolingo.controller.payload.response.GetResultResponse;
import com.example.duolingo.controller.payload.response.GetResultsResponse;
import com.example.duolingo.service.impl.ResultService;
import com.example.duolingo.validation.DuolingoRuntimeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duolingo/results")
@RequiredArgsConstructor
@Slf4j
public class ResultController {

  private final ResultService resultService;

  @PostMapping(path = "/{languageName}")
  public ResponseEntity<GetResultsResponse> getResults(
      @PathVariable final String languageName) throws DuolingoRuntimeException {

    return ResponseEntity.ok(resultService.findResultsForLanguage(languageName));
  }
  @PostMapping(path = "/get-result-details/{resultId}")
  public ResponseEntity<GetResultResponse> getResultDetails(
      @PathVariable final Long resultId) throws DuolingoRuntimeException {

    return ResponseEntity.ok(resultService.getResultDetails(resultId));
  }


}
