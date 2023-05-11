package com.scaler.taskmanager.tasks.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
public class UpdateTaskDto {

    LocalDate dueDate;
    Boolean completed;
}
