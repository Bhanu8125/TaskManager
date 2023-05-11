package com.scaler.taskmanager.tasks;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class Task {
    Integer id;
    String name;
    LocalDate dueDate;
    Boolean completed;

    public Task(String name, LocalDate dueDate, Boolean completed) {
        this.name = name;
        this.dueDate = dueDate;
        this.completed = completed;
    }
}
