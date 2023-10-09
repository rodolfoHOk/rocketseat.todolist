package br.com.hiokdev.todolist.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiokdev.todolist.user.model.UserModel;

@RestController
@RequestMapping("/users")
public class UserController {
  
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public UserModel create(@RequestBody UserModel userModel) {
    System.out.println(userModel);
    return userModel;
  }

}
