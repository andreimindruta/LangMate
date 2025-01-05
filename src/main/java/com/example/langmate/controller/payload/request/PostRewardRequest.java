package com.example.langmate.controller.payload.request;

import lombok.NonNull;

public record PostRewardRequest(
        @NonNull String name,
        @NonNull String description
) {
}
