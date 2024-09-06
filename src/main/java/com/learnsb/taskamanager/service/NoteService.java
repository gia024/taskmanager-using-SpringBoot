package com.learnsb.taskamanager.service;

import com.learnsb.taskamanager.entities.NoteEntity;
import com.learnsb.taskamanager.entities.taskEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NoteService {
private taskService taskservice;
private HashMap<Integer, TaskNotesHolder> taskNoteHolders = new HashMap<>();

    public NoteService(taskService taskservice) {
        this.taskservice = taskservice;
    }
    class TaskNotesHolder{
        protected int noteId = 1;
        protected ArrayList<NoteEntity> notes = new ArrayList<>();

    }
    public List<NoteEntity> getNotesForTask(int taskId) {
        taskEntity task = taskService.getTaskById(taskId);
        if(task == null) {
            return null;
        }
        if(taskNoteHolders.get(taskId) ==null){
            taskNoteHolders.put(taskId, new TaskNotesHolder());        }
        return taskNoteHolders.get(taskId).notes;

    }

    public NoteEntity addNoteForTask(int taskId, String title, String body) {
        taskEntity task = taskService.getTaskById(taskId);
        if(task == null) {
            return null;

        }
        if(taskNoteHolders.get(taskId) ==null){
            taskNoteHolders.put(taskId, new TaskNotesHolder());
        }
        TaskNotesHolder taskNoteHolder = taskNoteHolders.get(taskId);
        NoteEntity note = new NoteEntity();

        note.setTitle(title);
        note.setBody(body);
        note.setId(taskNoteHolder.noteId);
        taskNoteHolder.notes.add(note);
        taskNoteHolder.noteId++;
        return note;

    }
}
