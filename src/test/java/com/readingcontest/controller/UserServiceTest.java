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
import com.readingcontest.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService service;

	@Test(expected = DuplicatedUserException.class)
	public void shouldThrowDuplicatedException() throws Exception {
		User newUser = new User("Felipe Pereira", "123", true);

		// @formatter:off
		when(service.getUserByName(ArgumentMatchers.any(String.class)))
			.thenReturn(Optional.of(newUser));
		
		service.createUser(newUser);
		// @formatter:on
	}

}
