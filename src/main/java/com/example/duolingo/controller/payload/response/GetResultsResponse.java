package com.example.duolingo.controller.payload.response;

import java.util.List;
import lombok.NonNull;

public record GetResultsResponse(@NonNull List<GetResultResponse> results) {

}
