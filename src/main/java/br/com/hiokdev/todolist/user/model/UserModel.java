package br.com.hiokdev.todolist.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
  
  private String username;
  private String name;
  private String password;

}
