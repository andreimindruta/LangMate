package com.example.langmate.controller.payload.response;

import lombok.NonNull;

public record GetRewardResponse(
        @NonNull Long id,
        @NonNull String name,
        @NonNull String description
) {
}
