package com.readingcontest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Not implemented")
public class NotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
