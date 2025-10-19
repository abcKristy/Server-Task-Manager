package com.example.Task.errors;

import java.time.LocalDate;

public record ErrorResponseDto(
        String message,
        String detailedMassage,
        LocalDate errortime
) {
}