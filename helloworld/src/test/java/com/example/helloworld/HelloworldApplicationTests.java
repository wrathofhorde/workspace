package com.example.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.UserDto;

import lombok.experimental.var;

@SpringBootTest
class HelloworldApplicationTests {

	@Test
	void contextLoads() {
	}

	@GetMapping(value = "/test")
	public UserDto test() {
		final UserDto userDto = new UserDto();
		userDto.setAge(12);
		userDto.setName("Hello");

		return userDto;
	}

}
