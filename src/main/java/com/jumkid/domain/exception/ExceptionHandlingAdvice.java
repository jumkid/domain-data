package com.jumkid.domain.exception;

import com.jumkid.share.controller.response.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(BAD_REQUEST)
    public CustomErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("The provided argument is missing or invalid.", ex);
        return CustomErrorResponse.builder()
                .timestamp(Calendar.getInstance().getTime())
                .property(ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList()))
                .details(ex.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()))
                .build();
    }


    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(BAD_REQUEST)
    public CustomErrorResponse handleMissingRequestParameterException(MissingServletRequestParameterException ex) {
        log.warn("The provided argument is missing or invalid.", ex);
        return CustomErrorResponse.builder()
                .timestamp(Calendar.getInstance().getTime())
                .property(List.of(ex.getParameterName()))
                .details(List.of(Objects.requireNonNull(ex.getMessage())))
                .build();
    }

    @ExceptionHandler({DomainDataNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public CustomErrorResponse handleDomainDataNotFoundException(DomainDataNotFoundException ex) {
        log.warn("Domain data is not found", ex);
        return CustomErrorResponse.builder()
                .timestamp(Calendar.getInstance().getTime())
                .message(ex.getMessage())
                .build();
    }
}
