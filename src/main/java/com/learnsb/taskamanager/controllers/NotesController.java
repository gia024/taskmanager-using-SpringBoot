package com.learnsb.taskamanager.controllers;

import com.learnsb.taskamanager.dto.CreateNoteDTO;
import com.learnsb.taskamanager.dto.CreateNoteResponseDTO;
import com.learnsb.taskamanager.entities.NoteEntity;
import com.learnsb.taskamanager.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.learnsb.taskamanager.service.taskService.taskId;

@RestController
@RequestMapping("/tasks/{taskid}/notes")
public class NotesController {
    private final NoteService noteService;

    public NotesController (NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<NoteEntity>> getNotes(@PathVariable("taskId")Integer taskid) {
        var notes = noteService.getNotesForTask(taskId);

        return ResponseEntity.ok(notes);
    }
    @PostMapping("")
    public ResponseEntity<NoteEntity> addNote(@PathVariable("taskId")Integer taskId,
                                              @RequestBody CreateNoteDTO body) {
        var note = noteService.addNoteForTask(taskId, body.getTitle(), body.getBody());

        return ResponseEntity.ok(new CreateNoteResponseDTO(taskId, note).getNote());
    }
}
