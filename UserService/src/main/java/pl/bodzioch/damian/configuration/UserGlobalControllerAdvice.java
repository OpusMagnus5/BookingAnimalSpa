package pl.bodzioch.damian.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bodzioch.damian.dto.UserAppErrorResponse;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.valueobject.ErrorDetailParameter;

@Slf4j
@Order(2)
@RestControllerAdvice
@RequiredArgsConstructor
class UserGlobalControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<UserAppErrorResponse> handleUserAppException(Exception e) {
        log.error(e.getMessage(), e);
        UserAppException generalError = UserAppException.getGeneralError();
        log.error(e.getMessage(), e);
        return getResponseEntity(generalError, messageSource);
    }

    static ResponseEntity<UserAppErrorResponse> getResponseEntity(UserAppException exception, MessageSource messageSource) {
        String errorCode = exception.getErrorCode().value();
        Object[] messageParams = exception.getParameters().stream().map(ErrorDetailParameter::value).toArray();
        String errorDetail = messageSource.getMessage(errorCode, messageParams, LocaleContextHolder.getLocale());
        UserAppErrorResponse response = new UserAppErrorResponse(exception, errorDetail);
        return ResponseEntity.status(exception.getHttpStatus().getCode())
                .body(response);
    }
}
