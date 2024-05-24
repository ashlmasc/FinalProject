package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

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
		User loggedInUser = userRepo.findByUsername(username);
		if (!loggedInUser.getRole().equals("admin")) {
			throw new Error("Not authorized");
		}
		users = userRepo.findAll();
		return users;
	}

	@Override
	public User show(String username, int id) {
		User loggedInUser = userRepo.findByUsername(username);
        if (loggedInUser != null) {
        	if (!loggedInUser.getRole().equals("admin")) {
        		throw new EntityNotFoundException("Only ADMIN can see user with: " + id);
        	}
            User existingUser = userRepo.findById(id);
            if (existingUser != null) {
                return existingUser;
            } else {
                throw new EntityNotFoundException("User not found with id: " + id);
            }
            
        } else {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
	}

	@Override
	public User update(String username, int id, User user) {
		User loggedInUser = userRepo.findByUsername(username);
        if (loggedInUser != null) {
        	if (!loggedInUser.getRole().equals("admin")) {
        		throw new EntityNotFoundException("Only ADMIN can update user with: " + id);
        	}
            User existingUser = userRepo.findById(id);
            if (existingUser != null) {
            	existingUser.setUsername(user.getUsername());
            	existingUser.setFirstName(user.getFirstName());
            	existingUser.setLastName(user.getLastName());
            	existingUser.setCohort(user.getCohort());
            	existingUser.setEnabled(user.getEnabled());
            	existingUser.setRole(user.getRole());
            	// consider how the relationship mapping needs to be updated and the password
            	// password
            	// questions
            	// quizzes
            	// quizAnswers
                return userRepo.saveAndFlush(existingUser);
            } else {
                throw new EntityNotFoundException("User not found with id: " + id);
            }
        } else {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
	}

	@Override
	public boolean destroy(String username, int id) {
		User loggedInUser = userRepo.findByUsername(username);
        if (loggedInUser != null) {
        	if (!loggedInUser.getRole().equals("admin")) {
        		throw new EntityNotFoundException("Only ADMIN can delete user: " + id);
        	}
            User existingUser = userRepo.findById(id);
            if (existingUser != null) {
            	existingUser.setEnabled(false);
            	userRepo.saveAndFlush(existingUser);
            	return true;
            } else {
                throw new EntityNotFoundException("User not found with id: " + id);
            }
        } else {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
	}
}
