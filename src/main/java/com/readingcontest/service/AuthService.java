package com.readingcontest.service;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readingcontest.dao.UserCredentialsRepository;
import com.readingcontest.exception.AuthenticationException;
import com.readingcontest.exception.NotImplementedException;

@Service
public class AuthService {

	@Autowired
	private UserCredentialsRepository repository;

	/**
	 * Gets an user by username and password, throwing and Exception in 
	 * case no user/password could be found.
	 * @param username
	 * @param password
	 * @return
	 * @throws AuthException throw in case no user found 
	 */
	public void login(String username, String password) throws AuthenticationException {
		// @formatter:off
		repository.findByNameAndPassword(username, password)
			.orElseThrow(AuthenticationException::new);
		// @formatter:on
	}

	public void changePassword(Long id, String password) throws AuthenticationException, NotImplementedException {
		throw new NotImplementedException();
	}

}
