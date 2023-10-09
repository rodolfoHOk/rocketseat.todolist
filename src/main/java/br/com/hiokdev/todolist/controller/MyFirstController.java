package br.com.hiokdev.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-route")
public class MyFirstController {
  
  @GetMapping("/first-method")
  public String firstMessage() {
    return "It worked!";
  }

}
