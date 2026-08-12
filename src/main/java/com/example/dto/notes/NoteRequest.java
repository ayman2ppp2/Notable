package com.example.dto.notes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoteRequest(
    @NotBlank(message = "title is required")
    @Size(max = 255, message = "title must be at most 255 characters")
    String title, 
    
    @Size(max = 10000, message = "content must be at most 10000 characters")
    String content) {}
