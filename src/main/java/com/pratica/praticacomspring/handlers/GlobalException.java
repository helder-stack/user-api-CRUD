package com.pratica.praticacomspring.handlers;

import jakarta.annotation.Resource;
import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    private HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(String message, HttpStatus statusCode){
        ResponseError responseError = new ResponseError();
        responseError.setStatus("error");
        responseError.setError(message);
        responseError.setStatusCode(statusCode.value());
        return responseError;
    }
    @ExceptionHandler(Exception.class)
    private Object handleGeneral(Exception e, WebRequest request) {
        if (e.getClass().isAssignableFrom(UndeclaredThrowableException.class)) {
            UndeclaredThrowableException exception = (UndeclaredThrowableException) e;
            return this.responseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
    @ExceptionHandler({NotFoundException.class})
    private ResponseEntity<Object> handleNotfoundException(NotFoundException e, WebRequest request){
        ResponseError error = this.responseError(e.getMessage(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(e, error, this.httpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({BadRequestException.class})
    private ResponseEntity<Object> handleBadRequestException(BadRequestException e, WebRequest request){
        ResponseError error = this.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(e, error, this.httpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ForbiddenException.class})
    private ResponseEntity<Object> handleForbisddenRequestException(ForbiddenException e, WebRequest request){
        ResponseError error = this.responseError(e.getMessage(), HttpStatus.FORBIDDEN);
        return handleExceptionInternal(e, error, this.httpHeaders(), HttpStatus.FORBIDDEN, request);
    }

}
