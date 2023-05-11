package com.scaler.taskmanager.tasks;


import com.scaler.taskmanager.tasks.Interfaces.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TaskRepositoryImpl  implements TaskRepository {
    private Map<Integer,Task> taskMap ;
    private int nextTaskId;

    public TaskRepositoryImpl() {
        this.taskMap = new HashMap<>();
        nextTaskId = 1;
    }

    @Override
    public Task addTask(Task task) {
        task.setId(nextTaskId);
        taskMap.put(nextTaskId, task);
        nextTaskId++;
        return task;
    }

    @Override
    public Task getTaskById(int id) {
        return taskMap.getOrDefault(id, null);
    }

    @Override
    public Map<Integer,Task> getAllTasks() {
        if(taskMap.size()==0) return null;
        return taskMap;
    }

    @Override
    public void updateTask(int id, Task task) {
        taskMap.put(id, task);
    }

    @Override
    public void deleteTask(int id) {
        taskMap.remove(id);
    }

}
