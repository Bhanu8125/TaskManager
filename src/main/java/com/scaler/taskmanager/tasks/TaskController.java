package com.scaler.taskmanager.tasks;

import com.scaler.taskmanager.tasks.dto.CreateTaskDto;
import com.scaler.taskmanager.tasks.dto.ErrorMessage;
import com.scaler.taskmanager.tasks.dto.ResponseDto;
import com.scaler.taskmanager.tasks.dto.UpdateTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
   private final TaskServiceImpl taskService;

    public TaskController(@Autowired TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> createTask(@RequestBody CreateTaskDto createTaskDto){
        Task savedTask = taskService.createTask(createTaskDto.getName(), createTaskDto.getDueDate());
        ResponseDto responseDto = new ResponseDto(savedTask.getId(),savedTask.getName(), savedTask.getDueDate(), savedTask.getCompleted());
        return ResponseEntity.ok(responseDto);
        //return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseDto getTaskById(@PathVariable("id") Integer id)  {
        Task task = taskService.getTaskById(id);
        ResponseDto responseDto = new ResponseDto(task.getId(),task.getName(), task.getDueDate(), task.getCompleted());
        return responseDto;
    }

    @GetMapping("")
    public ResponseEntity<List<Task>> getAllByTasks(
            @RequestParam(value = "beforeDate", required = false)LocalDate beforeDate,
            @RequestParam(value = "afterDate", required = false)LocalDate afterDate,
            @RequestParam(value = "completed", required = false)Boolean completed,
            @RequestParam(value = "ascending", required = false) Boolean asc,
            @RequestParam(value = "descending", required = false) Boolean desc
            ){
            var taskFilter = TaskServiceImpl.TaskFilter.fromQueryParams(beforeDate,afterDate,completed);
            var sorter = TaskServiceImpl.Sorter.fromQueryParams(asc,desc);
            //System.out.println("taskfilter  " + taskFilter +", " + beforeDate +", " + afterDate +", " + completed);
            //System.out.println("sorter  "  +sorter +", " + asc +", " +  desc);
            List<Task> tasks = taskService.getAllTasks(taskFilter, sorter);
            return ResponseEntity.ok(tasks);
    }
    /*
    @GetMapping("")
    public ResponseEntity<List<Task>> getAllByTasks(){
        List<Task> tasks = taskService.getAllTasks2();
        return ResponseEntity.ok(tasks);
    }
    */


    @PatchMapping("/{id}")
    public ResponseDto updateDueDate(@PathVariable("id") Integer id,
                              @RequestBody UpdateTaskDto updateTaskDto){
        Task updatedTask = taskService.updateTask(id, updateTaskDto.getDueDate(), updateTaskDto.getCompleted());
        ResponseDto responseDto = new ResponseDto(updatedTask.getId(),updatedTask.getName(), updatedTask.getDueDate(), updatedTask.getCompleted());
        return  responseDto;
    }

        @DeleteMapping("/{id}")
        ResponseEntity<String> deleteTask(@PathVariable("id") Integer id) {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Successfully Deleted");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorMessage handleIllegalArguementException(IllegalArgumentException e){
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(TaskServiceImpl.TaskNotFoundException.class)
    @ResponseBody
    public ErrorMessage handleTaskNotFoundException(TaskServiceImpl.TaskNotFoundException e){
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return errorMessage;
    }
}
