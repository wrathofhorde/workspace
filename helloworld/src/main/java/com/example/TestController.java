package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/test")
  public UserDto getUserDto() {
    UserDto userDto = new UserDto("Hello", 20);
    System.out.println(userDto);
    userDto.setAge(21);
    userDto.setName("Hello, world");

    return userDto;
  }
}
