package com.example.duolingo.controller.payload.request;

import lombok.NonNull;

public record SaveResultRequest(@NonNull  Double grade,@NonNull String languageName) {

}
