package com.wusly.backendmenu.controller.errorhandler;


import com.wusly.backendmenu.controller.model.ErrorResponse;
import com.wusly.backendmenu.error.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestErrorHandler {


    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ErrorResponse handleException(Throwable e) {
        log.error(ErrorCode.UNKNOWN.name() + " error occurred! Message: {} ", e.getMessage());

        var message = "An unknown error occurred!";
        return ErrorResponse.of(ErrorCode.UNKNOWN.name(), message);
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handle(ApiException e) {
        var status = switch (e.getCode()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case UNKNOWN -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };

        return ResponseEntity
                .status(status)
                .body(ErrorResponse.of(e.getCode().name(), e.getMessage(), e.getDetail()));
    }

    @ExceptionHandler
    @ResponseStatus(UNAUTHORIZED)
    ErrorResponse handle(WrongUserNamePasswordException e) {
        return ErrorResponse.of(ErrorCode.WRONG_USERNAME_PASSWORD.name(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse handle(AlreadyExistsException e) {
        return ErrorResponse.of(ErrorCode.ALREADY_EXISTS.name(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    ErrorResponse handle(DataIntegrityViolationException e) {
        if (e.getMessage().contains("num_unique"))
            return ErrorResponse.of(ErrorCode.ALREADY_EXISTS.name(), "Bu masa daha önceden oluşturuldu !");
        return ErrorResponse.of(ErrorCode.UNKNOWN.name(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    ErrorResponse handle(NotPermittedException e) {
        return ErrorResponse.of(ErrorCode.FORBIDDEN.name(), e.getMessage());
    }

}
