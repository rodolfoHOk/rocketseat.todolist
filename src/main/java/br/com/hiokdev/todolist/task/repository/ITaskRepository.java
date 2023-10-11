package br.com.hiokdev.todolist.task.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hiokdev.todolist.task.model.TaskModel;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
  
}
