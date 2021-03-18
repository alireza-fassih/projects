package ir.fassih.homework.certmanager.rest;

import ir.fassih.homework.certmanager.rest.model.ApiErrorResponse;
import ir.fassih.homework.certmanager.service.locale.LocaleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomRestExceptionHandler {

    private final LocaleUtils localeUtils;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiErrorResponse badCredentialsExceptionHandler(BadCredentialsException ex) {
        return ApiErrorResponse.builder()
                .error("CredentialsNotFound")
                .message(localeUtils.getString("auth-error-credentials-not-found"))
                .build();
    }


}
