package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepo;

	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public List<User> getAllUsers(String username) {
		List<User> users = null;
		User user = userRepo.findByUsername(username);
		if (!user.getRole().equals("admin")) {
			throw new Error("Not authorized");
		}
		users = userRepo.findAll();
		return users;
	}
}
