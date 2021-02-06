package com.company.provider.config;

import com.company.provider.exeption.ApiError;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    private ResourceBundleMessageSource messageSource;

    public ErrorHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleRestException(RestException e) {
        String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), message);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleEntityException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(messageSource.getMessage(error.getDefaultMessage(), null, LocaleContextHolder.getLocale()));
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), errors);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
