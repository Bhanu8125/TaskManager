package com.scaler.taskmanager.tasks;


import com.scaler.taskmanager.tasks.Interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepositoryImpl taskRepository;

    public TaskServiceImpl(@Autowired TaskRepositoryImpl taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(String name, LocalDate dueDate) {

        if (name.length() < 5 || name.length() > 100)
            throw new IllegalArgumentException("Given Name Length is Invalid, should be in between 5 to 100 characters");
        if (dueDate.equals(null) || checkDueDate(dueDate))
            throw new IllegalArgumentException("Given Due Date is Invalid,Please assign Future Date");
        Task task = new Task(name, dueDate, false);
        return taskRepository.addTask(task);
    }
//    public boolean checkDueDate(Date dueDate){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String formatedDate = formatter.format(date);
//        String formateddueDate = formatter.format(dueDate);
//        Date dateCheck = new Date(formateddueDate);
//        if(dateCheck.before(new Date(formatedDate)))
//        {
//            System.out.println("wrong date");
//            return true;
//        }
//        return false;
//    }
    public boolean checkDueDate(LocalDate dueDate) {
        if (dueDate.compareTo(LocalDate.now()) < 0) return true;
        return false;
    }

    @Override
    public Task getTaskById(int id) throws TaskNotFoundException {
        Task task = taskRepository.getTaskById(id);
        if (task == null) throw new TaskNotFoundException(id);
        return task;
    }


    @Override
    public List<Task> getAllTasks(TaskFilter taskFilter, Sorter sorter) {
        Map<Integer, Task> savedTasks = taskRepository.getAllTasks();
        if (savedTasks == null) return new ArrayList<>();
        ArrayList<Task> taskList = new ArrayList<>(savedTasks.values());
        if (taskFilter == null && sorter == null) return taskList;
        List<Task> finalList = null;
        if (taskFilter != null) {
            finalList = taskList.stream().filter(task -> {
                if (taskFilter.beforeDate != null && taskFilter.beforeDate.compareTo(task.getDueDate()) < 0) {
                    return false;
                }
                if (taskFilter.afterDate != null && taskFilter.afterDate.compareTo(task.getDueDate()) > 0) {
                    return false;
                }
                if (taskFilter.completed != null && taskFilter.completed != task.getCompleted()) {
                    return false;
                }
                return true;
            }).toList();
        }
        else{
            finalList = taskList;
        }
        if (sorter != null) {
             List<Task> sortedList = finalList.stream().sorted(new CustomComp()).collect(Collectors.toList());
            if (sorter.desc !=null && sorter.desc) { Collections.reverse(sortedList) ;}
            return sortedList;
        }
        return finalList;
    }
    public List<Task> getAllTasks2() {
        Map<Integer, Task> savedTasks = taskRepository.getAllTasks();
        if(savedTasks==null) return new ArrayList<>();
        ArrayList<Task> taskList = new ArrayList<>(savedTasks.values());
        return taskList;
    }
    @Override
    public Task updateTask(int id, LocalDate dueDate, Boolean completed) {
        Task task = getTaskById(id);
        if(dueDate!=null && checkDueDate(dueDate)) throw new IllegalArgumentException("Given Due Date is Invalid,Please assign Future Date");
        if(dueDate!=null) task.setDueDate(dueDate);
        if(completed!=null) task.setCompleted(completed);
        taskRepository.updateTask(id, task);
        return task;
    }
    @Override
    public void deleteTask(Integer id) {
        Task task = getTaskById(id);
        taskRepository.deleteTask(id);
    }
    class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(Integer id) {
            super("Task with id " + id + " not found");
        }
    }

    public static class TaskFilter{
        LocalDate beforeDate;
        LocalDate afterDate;
        Boolean completed;
        static TaskFilter fromQueryParams(LocalDate beforeDate, LocalDate afterDate, Boolean completed){
            //System.out.println(completed +"   " + "completed");
            if(beforeDate==null && afterDate==null && completed==null){
                return  null;
            }
            TaskFilter taskFilter = new TaskFilter();
            taskFilter.beforeDate = beforeDate;
            taskFilter.afterDate = afterDate;
            taskFilter.completed = completed;
            return taskFilter;
        }
    }

    public static class Sorter{
        Boolean asc;
        Boolean desc;
         static Sorter fromQueryParams(Boolean asc, Boolean desc) {
             if (asc == null && desc == null) return null;
             Sorter sorter = new Sorter();
             sorter.asc = asc;
             sorter.desc = desc;
             return sorter;
         }
    }
    public class CustomComp implements  Comparator<Task>{

        @Override
        public int compare(Task t1, Task t2) {
            if(t1.getDueDate().compareTo(t2.getDueDate())>0) return 1;
            return -1;
        }
    }





}
