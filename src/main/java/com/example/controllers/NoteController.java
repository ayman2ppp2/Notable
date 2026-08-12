package com.example.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.notes.NoteRequest;
import com.example.dto.notes.NoteResponse;
import com.example.services.NoteService;

/**
 * NoteController
 */
 @RestController
 @RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteResponse> findAll() {
        return noteService.findAll();
    }

    @PostMapping
    public NoteResponse create(@RequestBody NoteRequest request) {
        return noteService.create(request);
    }

    @GetMapping("/{id}")
    public NoteResponse findById(@PathVariable Long id) {
        return noteService.findById(id);
    }

    @PutMapping("/{id}")
    public NoteResponse update(@PathVariable Long id, @RequestBody NoteRequest request) {
        return noteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }
}