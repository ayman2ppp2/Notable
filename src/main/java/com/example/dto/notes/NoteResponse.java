package com.example.dto.notes;

import com.example.models.Note;
import java.time.LocalDateTime;

public record NoteResponse(
    long id,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static NoteResponse from(Note note) {
        return new NoteResponse(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }
}
