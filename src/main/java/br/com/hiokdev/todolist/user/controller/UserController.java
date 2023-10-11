package br.com.hiokdev.todolist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.hiokdev.todolist.user.model.UserModel;
import br.com.hiokdev.todolist.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final IUserRepository userRepository;
  
  @PostMapping
  public ResponseEntity<?> create(@RequestBody UserModel userModel) {
    var existUser = userRepository.findByUsername(userModel.getUsername());

    if (existUser.isPresent()) {
      return ResponseEntity.badRequest().body("Usuário já existe");
    }

    var passwordHashed = BCrypt.withDefaults()
      .hashToString(12, userModel.getPassword().toCharArray());
    userModel.setPassword(passwordHashed);

    var userCreated = userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

}
