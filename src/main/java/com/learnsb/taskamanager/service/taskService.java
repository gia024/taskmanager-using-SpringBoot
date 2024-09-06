package com.learnsb.taskamanager.service;

import com.learnsb.taskamanager.entities.taskEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class taskService {
    private static ArrayList<taskEntity> tasks = new ArrayList<taskEntity>();
    public static int taskId = 1;
    private static final SimpleDateFormat deadlineformatter = new SimpleDateFormat("dd/MM/yyyy");

//Adding task
   public static taskEntity addTask(String title, String description, String deadline) throws ParseException {
        taskEntity task = new taskEntity();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadlineformatter.parse(deadline)); //TODO validate date format yyy-mm-dd
        task.setCompleted(false);
        tasks.add(task);
        taskId++;
        return task;
    }

    // get task

   public static ArrayList<taskEntity> getTasks(){
        return tasks;
    }

    //get task by id
    public static taskEntity getTaskById(int id){
        for(taskEntity task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }

    //update task

    public static taskEntity updateTask(int id, String description, String deadline, Boolean completed) throws ParseException {

       taskEntity task = getTaskById(id);
       if (task==null){
           return null;
       }
       if(description!=null){
           task.setDescription(description);
       }
       if(deadline!=null){
         task.setDeadline(deadlineformatter.parse(deadline));
       }
       if(completed!=null){
           task.setCompleted(completed);
       }

       return task;

    }
}
