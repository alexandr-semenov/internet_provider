package com.company.provider.config;

import com.company.provider.exeption.ApiError;
import com.company.provider.exeption.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    private final ResourceBundleMessageSource messageSource;

    public ErrorHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ApiError> handleRestException(RestException e) {
        String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        logger.error(message);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), message);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleEntityException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            String message = messageSource.getMessage(error.getDefaultMessage(), null, LocaleContextHolder.getLocale());
            logger.error(message);
            errors.add(message);
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), errors);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleEntityException(HttpMessageNotReadableException e) {
        String message = messageSource.getMessage("wrong_data_type_error", null, LocaleContextHolder.getLocale());
        logger.error(message);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), message);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
