package com.readingcontest.service;

import java.util.Optional;
import java.util.function.Predicate;

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

	public User createUser(User user) throws DuplicatedUserException {

		if (this.getUserByName(user.getName()).isPresent()) {
			throw new DuplicatedUserException();
		}

		return repository.save(user);
	}
	
	public User updateUser(User user) throws UserNotFoundException, DuplicatedUserException {
		
		// @formatter:off
		this.getUserById(user.getId())
			.orElseThrow(UserNotFoundException::new);
		
		this.getUserByName(user.getName())
			.or(() -> Optional.of(user))
			.filter(isTheSameUser(user))
			.orElseThrow(DuplicatedUserException::new);
		// @formatter:on
		
		return repository.save(user);
	}

	private Predicate<? super User> isTheSameUser(User user) {
		return u -> u.getId().equals(user.getId());
	}

	public Optional<User> getUserByName(String name) {
		return repository.findByName(name);
	}

	public Optional<User> getUserById(Long id) {
		return repository.findById(id);
	}

	public Page<User> findAll(Integer page, Integer size) {
		return repository.findAll(PageRequest.of(page, size));
	}

}
