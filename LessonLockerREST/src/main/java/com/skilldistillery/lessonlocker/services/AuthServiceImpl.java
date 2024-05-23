package com.skilldistillery.lessonlocker.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	
	private PasswordEncoder encoder;
	private UserRepository userRepo;
	
	public AuthServiceImpl(PasswordEncoder encoder, UserRepository userRepo) {
		super();
		this.encoder = encoder;
		this.userRepo = userRepo;
	}

	@Override
	public User register(User user) {
		// encrypt and set password for user
		String encryptedPassword = encoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		
		//other business logic related to registering a new account
		user.setEnabled(true);
		user.setRole("standard"); // standard // default // student // instructor // admin
		user.setCohort("default");

		//persist new user to db
		userRepo.saveAndFlush(user);
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}
