package com.readingcontest.service;

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
		
		service.createUser(newUser);
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
		User userBeingUpdated = new User("teste", "felipe.dspereira@gmail.com", "123", false);
		userBeingUpdated.setId(1l);
		
		User differentUser = new User("db user", "db@test.com", "123", false);
		differentUser.setId(233l); // it is a different user
		
		// @formatter:off
		when(repository.findById(userBeingUpdated.getId()))
			.thenReturn(Optional.of(userBeingUpdated));
		
		when(repository.findByName(userBeingUpdated.getName()))
			.thenReturn(Optional.of(differentUser));
		// @formatter:on
		
		service.updateUser(userBeingUpdated);
	}
	
	@Test
	public void shouldUpdateUser() throws UserNotFoundException, DuplicatedUserException {
		User userBeingUpdated = new User("test", "test@test.com", "123", false);
		userBeingUpdated.setId(1l);
		
		when(service.getUserById(userBeingUpdated.getId()))
			.thenReturn(Optional.of(userBeingUpdated));
		
		when(service.getUserByName(userBeingUpdated.getName()))
			.thenReturn(Optional.of(userBeingUpdated));
		
		service.updateUser(userBeingUpdated);
	}
	
	@Test
	public void shouldUpdateUserWhenNameNotFoundInDatabase() throws UserNotFoundException, DuplicatedUserException {
		User userBeingUpdated = new User("test", "test@test.com", "123", false);
		userBeingUpdated.setId(1l);
		
		when(service.getUserById(userBeingUpdated.getId()))
			.thenReturn(Optional.of(userBeingUpdated));
		
		when(service.getUserByName(userBeingUpdated.getName()))
			.thenReturn(Optional.empty());
		
		service.updateUser(userBeingUpdated);
	}
}
