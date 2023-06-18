package com.chatApplication.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatApplication.userService.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

}
