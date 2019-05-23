package com.readingcontest.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
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

	@Autowired
	private MockMvc mockMvc;
	
	private static User user;
	private static ObjectMapper mapper;
	
	@BeforeClass
	public static void setUp() {
		user = new User("Felipe Pereira", "felipedspereira@gmail.com", "123", true);
		mapper = new ObjectMapper();
	}

	@Test
	public void shouldCreateNewUser() throws Exception {

		// @formatter:off
		when(service.saveUser(ArgumentMatchers.any(User.class)))
			.thenReturn(user);

		mockMvc.perform(
				post("/users")
				.content(mapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is(HttpStatus.CREATED.value()));
		// @formatter:on
	}

	@Test
	public void shouldUpdateExistingUser() throws Exception {
		// @formatter:off
		when(service.updateUser(ArgumentMatchers.any(User.class)))
			.thenReturn(user);
		
		mockMvc.perform(
				put("/users")
				.content(mapper.writeValueAsBytes(user))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is(HttpStatus.OK.value()));
		// @formatter:on
	}
	
	@Test
	public void shouldRetrieveUser() throws Exception {
		User dbUser = new User("Felipe", "Teste@test.com", "123", true);
		dbUser.setId(1l);
		
		// @formatter:off
		when(service.findUserById(ArgumentMatchers.any(Long.class)))
			.thenReturn(Optional.of(dbUser));
		
		mockMvc.perform(
				get("/users/" + 1)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(content().json(mapper.writeValueAsString(dbUser)));
		// @formatter:on
	}
	
	@Test
	public void shouldGetAlistOfUsers() throws Exception {
		List<User> users = new ArrayList<User>();
		users.addAll(Arrays.asList(
				new User("user A", "usera.test.com", "123", false), 
				new User("user B", "userb.test.com", "123", false)));

		// @formatter:off
		when(service.findAll(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
			.thenReturn(new PageImpl<User>(users));
		
		mockMvc.perform(
				get("/users")
				.param("page", "0")
				.param("size", "10")
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(2)))
		.andExpect(jsonPath("$.content.[1].name", equalTo("user B")));
		// @formatter:on
	}
}