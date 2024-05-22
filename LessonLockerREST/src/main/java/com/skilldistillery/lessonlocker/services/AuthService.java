package com.skilldistillery.lessonlocker.services;

import com.skilldistillery.lessonlocker.entities.User;

public interface AuthService {
	
	public User register(User user);
	public User getUserByUsername(String username);

}
