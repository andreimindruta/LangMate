package com.example.duolingo.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DuolingoRuntimeException extends Exception {

  private final int status;
  private final String message;
}
