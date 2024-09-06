package com.learnsb.taskamanager.entities;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class taskEntity {
    private int id;
    private String title;
    private String description;
    private Date deadline;
    private boolean completed;
}
