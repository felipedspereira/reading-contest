package com.readingcontest.controller;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.readingcontest.dao.UserRepository;
import com.readingcontest.domain.User;
import com.readingcontest.exception.DuplicatedUserException;
import com.readingcontest.exception.UserNotFoundException;
import com.readingcontest.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService service;

	@Test(expected = DuplicatedUserException.class)
	public void shouldThrowExceptionWhenCreatingAlreadyExistingUser() throws Exception {
		User newUser = new User("Felipe Pereira", "felipe.dspereira@gmail.com", "123", true);

		// @formatter:off
		when(service.getUserByName(ArgumentMatchers.any(String.class)))
			.thenReturn(Optional.of(newUser));
		// @formatter:on
		
		service.saveUser(newUser);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void shouldThrowExceptionWhenUpdatingUnexistingUser() throws DuplicatedUserException, UserNotFoundException {
		User unexistingUser = new User("Felipe Pereira", "felipedspereira@gmail.com", "123", false);
		unexistingUser.setId(12312l);

		// @formatter:off
		when(repository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.empty());
		// @formatter:on

		service.updateUser(unexistingUser);
	}
	
	@Test(expected = DuplicatedUserException.class) 
	public void shouldThrowExceptionWhenChangingUserNameToAlreadyRegisteredName() throws UserNotFoundException, DuplicatedUserException {
		User user = new User("teste", "felipe.dspereira@gmail.com", "123", false);
		
		// @formatter:off
		when(repository.findById(ArgumentMatchers.any()))
			.thenReturn(Optional.of(user));
		
		when(repository.findByName(ArgumentMatchers.anyString()))
			.thenReturn(Optional.of(user));
		// @formatter:on
		
		service.updateUser(user);
	}
	
	

}
