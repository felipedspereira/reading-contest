package com.readingcontest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.readingcontest.dao.UserRepository;
import com.readingcontest.domain.User;
import com.readingcontest.exception.DuplicatedUserException;
import com.readingcontest.exception.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public User saveUser(User user) throws DuplicatedUserException {

		if (this.getUserByName(user.getName()).isPresent()) {
			throw new DuplicatedUserException();
		}

		return repository.save(user);
	}
	
	public User updateUser(User user) throws UserNotFoundException, DuplicatedUserException {
		
		if (repository.findById(user.getId()).isEmpty()) {
			throw new UserNotFoundException();
		}
		
		if (this.getUserByName(user.getName()).isPresent()) {
			throw new DuplicatedUserException();
		}

		return repository.save(user);
	}

	public Optional<User> getUserByName(String name) {
		return repository.findByName(name);
	}

	public Optional<User> findUserById(Long id) {
		return repository.findById(id);
	}

	public Page<User> findAll(Integer page, Integer size) {
		return repository.findAll(PageRequest.of(page, size));
	}

}
