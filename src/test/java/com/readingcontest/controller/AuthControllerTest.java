package com.readingcontest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.readingcontest.exception.AuthenticationException;
import com.readingcontest.service.AuthService;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService service;
	
	@Test
	public void shouldSuccessfullyLoginUser() throws Exception {
		String username = "felipe";
		String password = "123";
		
		// @formatter:off
		mockMvc.perform(
				post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.param("username", username)
				.param("password", password))
		.andExpect(status().isOk());
		// @formatter:on
		
		verify(service).login(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	}
	
	@Test
	public void shouldRaiseExceptionWhenInvalidLogin() throws Exception {
		String username = "felipe";
		String password = "123";
		
		// @formatter:off
		doThrow(new AuthenticationException())
			.when(service).login(username, password);

		mockMvc.perform(
				post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.param("username", username)
				.param("password", password))
		.andExpect(status().isBadRequest())
		.andExpect(status().reason(containsString("Invalid username and/or password")));
		// @formatter:on
	}
	
	@Test
	public void shouldChangePassword() throws Exception {
		// @formatter:off
		mockMvc.perform(
				post("/auth/password")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.param("id", "434")
				.param("password", "123"))
		.andExpect(status().isOk());
		// @formatter:on
	}
	
}
