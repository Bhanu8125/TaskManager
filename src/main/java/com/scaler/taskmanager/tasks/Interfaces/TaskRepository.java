package com.scaler.taskmanager.tasks.Interfaces;



import com.scaler.taskmanager.tasks.Task;

import java.util.Map;

public interface TaskRepository {
    Task addTask(Task task);
    Task getTaskById(int id);
    Map<Integer,Task> getAllTasks();

    void updateTask(int id, Task task);

    void deleteTask(int id);
}
