package com.example.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{

    List<Note> findByOwnerId(Long OwnerId);

    Optional<Note> findByIdAndOwnerId(Long id, Long ownerId);
    
}