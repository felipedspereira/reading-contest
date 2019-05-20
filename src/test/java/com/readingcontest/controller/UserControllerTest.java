package com.readingcontest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingcontest.domain.User;
import com.readingcontest.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	private UserService service;

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateNewUser() throws Exception {
		User newUser = new User("Felipe Pereira", "123", true);

		// @formatter:off
		when(service.createUser(ArgumentMatchers.any(User.class)))
			.thenReturn(newUser);

		mockMvc.perform(
				post("/users")
				.content(mapper.writeValueAsString(newUser))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(HttpStatus.CREATED.value()));
		// @formatter:on
	}

}