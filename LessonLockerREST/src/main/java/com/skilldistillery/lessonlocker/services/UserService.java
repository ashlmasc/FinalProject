package com.skilldistillery.lessonlocker.services;

import java.util.List;

import com.skilldistillery.lessonlocker.entities.User;

public interface UserService {
	
	List<User> getAllUsers(String username);
	
	User show(String username, int id);

	User update(String username, int id, User user);

	boolean destroy(String username, int id);

}
