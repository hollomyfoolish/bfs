package com.hollomyfoolish.web.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hollomyfoolish.entity.UserEntity;
import com.hollomyfoolish.service.UserService;

@RestController
@RequestMapping("/users")
@Produces("application/json")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping( path = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@RequestMapping( path = "/", method = RequestMethod.POST)
	@Consumes("application/json")
	public ResponseEntity<?> addUser(@RequestBody UserEntity user){
		user = userService.addUser(user);
		return ResponseEntity.ok(user);
	}
}
