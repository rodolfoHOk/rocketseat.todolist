package br.com.hiokdev.todolist.task.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping
  private List<TaskModel> list(HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    return taskRepository.findByUserId((UUID) userId);
  }

  @PutMapping("/{taskId}")
  private ResponseEntity<?> update(
    @PathVariable UUID taskId,
    @RequestBody TaskModel taskModel,
    HttpServletRequest request  
  ) {
    var userId = request.getAttribute("userId");
    Optional<TaskModel> existTask = taskRepository.findById(taskId);
    if (existTask.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    if (!existTask.get().getUserId().equals((UUID) userId)) {
      return ResponseEntity.notFound().build();
    }
    taskModel.setId(taskId);
    taskModel.setUserId((UUID) userId);
    taskModel.setCreatedAt(existTask.get().getCreatedAt());
    var updatedTask = taskRepository.save(taskModel);
    return ResponseEntity.ok().body(updatedTask);
  }

}
