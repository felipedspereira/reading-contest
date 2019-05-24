package com.readingcontest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingcontest.domain.User;
import com.readingcontest.exception.DuplicatedUserException;
import com.readingcontest.exception.UserNotFoundException;
import com.readingcontest.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping(value = "{id}")
	public User getById(@PathVariable("id") Long id) {
		return service.getUserById(id).orElse(null);
	}

	@GetMapping
	public Page<User> findUsers(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		return service.findAll(page, size);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void create(@RequestBody User user) throws Exception {
		service.createUser(user);
	}

	@PutMapping
	public void update(@RequestBody User user) throws UserNotFoundException, DuplicatedUserException {
		service.updateUser(user);
	}
}
