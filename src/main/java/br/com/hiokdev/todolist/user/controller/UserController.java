package br.com.hiokdev.todolist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiokdev.todolist.user.model.UserModel;
import br.com.hiokdev.todolist.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final IUserRepository userRepository;
  
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public UserModel create(@RequestBody UserModel userModel) {
    var userCreated = userRepository.save(userModel);
    return userCreated;
  }

}
