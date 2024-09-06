package com.learnsb.taskamanager.controllers;

import com.learnsb.taskamanager.dto.CreateTaskDTO;
import com.learnsb.taskamanager.dto.ErrorResponseDTO;
import com.learnsb.taskamanager.dto.TaskResponseDTO;
import com.learnsb.taskamanager.dto.UpdateTaskDTO;
import com.learnsb.taskamanager.entities.taskEntity;
import com.learnsb.taskamanager.service.NoteService;
import com.learnsb.taskamanager.service.taskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final taskService taskservice;
    private final NoteService noteService;
    private ModelMapper modelMapper = new ModelMapper();


    public TaskController(taskService taskservice, NoteService noteservice, NoteService noteService) {
        this.taskservice = taskservice;
        this.noteService = noteService;
    }
    @GetMapping("")
    public ResponseEntity<List<taskEntity>> getTasks() {
        var tasks = taskService.getTasks();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable int id) {
        var task = taskService.getTaskById(id);
        var notes = noteService.getNotesForTask(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        var taskResponse = modelMapper.map(task, TaskResponseDTO.class);
        return ResponseEntity.ok(taskResponse);
    }
    @PostMapping("")
    public ResponseEntity<taskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
        var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<taskEntity> updateTask(@PathVariable int id, @RequestBody UpdateTaskDTO body) throws ParseException {
        var task = taskService.updateTask(id , body.getDescription(), body.getDeadline(), body.getCompleted());

        if(task == null){
        return ResponseEntity.notFound().build();
        }
    return ResponseEntity.ok(task);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors (Exception e) {
        if(e instanceof ParseException){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("invalid date format"));
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("internal server error"));
    }
}
