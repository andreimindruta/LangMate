package com.example.duolingo.controller.payload.response;


import java.util.List;

public record GetLanguagesResponse(List<GetLanguageResponse> languages) {

}
