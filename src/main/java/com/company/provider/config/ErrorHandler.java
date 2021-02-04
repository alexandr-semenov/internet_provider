package com.company.provider.config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    private ResourceBundleMessageSource messageSource;

    public ErrorHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestException handleCustomException(RestException e) {
        String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());

        return new RestException(message, e.getCause(), false, false);
    }
}
