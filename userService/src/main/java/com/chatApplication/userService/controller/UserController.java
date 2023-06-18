package com.chatApplication.userService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chatApplication.userService.dto.UserDto;
import com.chatApplication.userService.model.User;
import com.chatApplication.userService.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public User getUser(@RequestBody UserDto userDto) {
		
		return userService.getUser(userDto.getUsername());
	}
	
	@PostMapping("/save")
	@ResponseStatus(HttpStatus.OK)
	public String  saveUser(@RequestBody UserDto userDto) {
		
		User user = new User();

		user = userService.getUser(userDto.getUsername());

		if (user == null)
			user = userService.saveUser(userDto);
		
		return user.getUsername() +" "+user.getIdUser();
	}
	
	

}
