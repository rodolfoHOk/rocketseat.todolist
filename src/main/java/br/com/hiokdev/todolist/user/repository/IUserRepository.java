package br.com.hiokdev.todolist.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hiokdev.todolist.user.model.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
  
}
