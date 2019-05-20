package com.readingcontest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User already registered")
public class DuplicatedUserException extends Exception {

	private static final long serialVersionUID = 1L;

}
