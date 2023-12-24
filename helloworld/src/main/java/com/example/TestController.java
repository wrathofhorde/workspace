package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  
  @GetMapping("/test")
  public UserDto getUserDto() {
    UserDto userDto = new UserDto();
    userDto.setAge(20);
    userDto.setName("Hello");

    return userDto;
  }
}
