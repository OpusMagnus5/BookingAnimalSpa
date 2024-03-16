package pl.bodzioch.damian.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bodzioch.damian.dto.UserAppErrorResponse;
import pl.bodzioch.damian.exception.UserAppException;

@Slf4j
@Order(1)
@RestControllerAdvice
@RequiredArgsConstructor
class UserControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({UserAppException.class})
    ResponseEntity<UserAppErrorResponse> handleUserAppException(UserAppException e) {
        log.error(e.toString(), e);
        return UserGlobalControllerAdvice.getResponseEntity(e, messageSource);
    }
}
