package com.example.services;

import com.example.Security.CurrentUser;
import com.example.dto.notes.NoteRequest;
import com.example.dto.notes.NoteResponse;
import com.example.models.Note;
import com.example.models.User;
import com.example.repositories.NoteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional
    public List<NoteResponse> findAll() {
        return noteRepository
            .findByOwnerId(currentUserId())
            .stream()
            .map(NoteResponse::from)
            .toList();
    }

    @Transactional
    public NoteResponse create(NoteRequest request) {
        User owner = CurrentUser.get();
        Note note = new Note(request.title(), request.content(), owner);
        return NoteResponse.from(noteRepository.save(note));
    }

    @Transactional
    public NoteResponse findById(Long id) {
        return NoteResponse.from(findOwned(id));
    }


    @Transactional
    public NoteResponse update (Long id , NoteRequest request){
        Note note = findOwned(id);
        note.setTitle(request.title());
        note.setContent(request.content());
        return NoteResponse.from(noteRepository.save(note));
    }

    @Transactional 
    public void delete(Long id){
        noteRepository.delete(findOwned(id));
    }

    
    private Note findOwned(Long id) {
        return noteRepository
            .findByIdAndOwnerId(id, currentUserId())
            .orElseThrow();
    }

    private Long currentUserId() {
        return CurrentUser.get().getId();
    }
}
