package com.scaler.taskmanager.tasks.dto;

import lombok.Data;
import org.springframework.cglib.core.internal.LoadingCache;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CreateTaskDto {
    String name;
    LocalDate dueDate;
}
