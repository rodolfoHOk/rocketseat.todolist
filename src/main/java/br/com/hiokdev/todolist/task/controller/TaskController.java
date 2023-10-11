package br.com.hiokdev.todolist.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiokdev.todolist.task.model.TaskModel;
import br.com.hiokdev.todolist.task.repository.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {
  
  private final ITaskRepository taskRepository;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody TaskModel taskModel) {
    var taskCreated = taskRepository.save(taskModel);
    return ResponseEntity.ok().body(taskCreated);
  }

}
