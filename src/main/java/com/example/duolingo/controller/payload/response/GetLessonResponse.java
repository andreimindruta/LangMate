package com.example.duolingo.controller.payload.response;

import lombok.NonNull;

public record GetLessonResponse(@NonNull String title,@NonNull String text) {

}
