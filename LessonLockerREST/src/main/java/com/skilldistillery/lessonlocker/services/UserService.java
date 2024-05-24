package com.skilldistillery.lessonlocker.services;

import java.util.List;

import com.skilldistillery.lessonlocker.entities.User;

public interface UserService {
	
	List<User> getAllUsers(String username);

}
