package com.hollomyfoolish.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hollomyfoolish.entity.UserEntity;
import com.hollomyfoolish.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public List<UserEntity> getAllUsers(){
		return userRepo.findAll();
	}

	public UserEntity addUser(UserEntity user) {
		return userRepo.save(user);
	}
}
