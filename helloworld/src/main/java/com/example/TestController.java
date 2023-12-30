package com.example;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {
  
  @GetMapping("/test")
  public UserDto test() {
    UserDto userDto = new UserDto();
    userDto.setAge(20);
    userDto.setName("hello");
    return userDto;
  }
  
}
