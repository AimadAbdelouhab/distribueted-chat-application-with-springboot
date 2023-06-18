package com.chatApplication.userService.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatApplication.userService.dto.UserDto;
import com.chatApplication.userService.model.User;
import com.chatApplication.userService.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}
	
	
	public User saveUser(UserDto userDto) {
		
		User user = new User();
		user.setUsername(userDto.getUsername());
		
		return userRepository.save(user);
	}

}
