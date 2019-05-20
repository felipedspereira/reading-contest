package com.readingcontest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readingcontest.dao.UserRepository;
import com.readingcontest.domain.User;
import com.readingcontest.exception.DuplicatedUserException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public User createUser(User user) throws DuplicatedUserException {

		if (this.getUserByName(user.getName()).isPresent()) {
			throw new DuplicatedUserException();
		}

		return repository.save(user);
	}

	public Optional<User> getUserByName(String name) {
		return repository.findByName(name);
	}

}
