package com.scaler.taskmanager.tasks.dto;


import com.scaler.taskmanager.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class ResponseDto {
    Integer id;
    String name;
    LocalDate dueDate;
    Boolean completed;
}
