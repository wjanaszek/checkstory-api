package org.wjanaszek.checkstory.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class AppException {

    @ExceptionHandler(value = {BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError badCredentials(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage());
        return new ApiError(400, HttpStatus.BAD_REQUEST.getReasonPhrase() + ": " + e.getMessage());
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class, BadRequestException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError badRequest(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage());
        return new ApiError(400, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(value = {NoResourceFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError notFound(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage());
        return new ApiError(404, HttpStatus.NOT_FOUND.getReasonPhrase() + ": " + e.getMessage());
    }

}
