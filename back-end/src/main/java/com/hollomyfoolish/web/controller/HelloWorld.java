package com.hollomyfoolish.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorld {

	@RequestMapping( path = "/echo/{q}", method = RequestMethod.GET)
	public ResponseEntity<?> uploadAudio(@PathVariable("q") String q, HttpServletRequest req){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 0);
		result.put("message", q);
		return ResponseEntity.ok(result);
	}
}
