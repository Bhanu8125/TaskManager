package com.scaler.taskmanager.tasks.Interfaces;


import com.scaler.taskmanager.tasks.Task;
import com.scaler.taskmanager.tasks.TaskServiceImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TaskService {

    Task createTask(String name, LocalDate dueDate);
    Task getTaskById(int id) ;
    List<Task> getAllTasks(TaskServiceImpl.TaskFilter taskFilter, TaskServiceImpl.Sorter sorter);
    Task updateTask(int id, LocalDate dueDate, Boolean completed);

    void deleteTask(Integer id);
}
