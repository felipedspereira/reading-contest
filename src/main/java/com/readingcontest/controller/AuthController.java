package com.readingcontest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readingcontest.exception.AuthenticationException;
import com.readingcontest.service.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthService service;
	
	@PostMapping(value = "/login")
	public void login(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
		service.login(username, password);
	}
	
	@PutMapping(value = "/password")
	public void changePassword(@RequestParam("id") Long id, @RequestParam("password") String password) throws AuthenticationException {
		service.changePassword(id, password);
	}
}
