package br.com.hiokdev.todolist.task.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiokdev.todolist.task.model.TaskModel;
import br.com.hiokdev.todolist.task.repository.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {
  
  private final ITaskRepository taskRepository;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    taskModel.setUserId((UUID) userId);

    var currentDate = LocalDateTime.now();
    System.out.println(currentDate.toString());
    if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
      System.out.println(taskModel.getStartAt());
      return ResponseEntity.badRequest().body("A data de início / término deve ser maior a data atual");
    }
    if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity.badRequest().body("A data de término deve ser maior a data de início");
    }
    
    var taskCreated = taskRepository.save(taskModel);
    return ResponseEntity.ok().body(taskCreated);
  }

}
