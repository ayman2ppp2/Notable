package com.example.dto.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "content must be between 10 and 255 characters long")
        @Size(min = 10, max = 255)
        String content,
        
        boolean done
) {
}
