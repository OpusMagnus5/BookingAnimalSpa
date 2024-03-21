package pl.bodzioch.damian.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bodzioch.damian.dto.ErrorDto;
import pl.bodzioch.damian.dto.UserAppErrorResponse;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.ErrorCode;
import pl.bodzioch.damian.valueobject.ErrorSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Order(1)
@RestControllerAdvice
@RequiredArgsConstructor
class UserControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({UserAppException.class})
    ResponseEntity<UserAppErrorResponse> handleUserAppException(UserAppException e) {
        log.error(e.getMessage(), e);
        return UserGlobalControllerAdvice.getResponseEntity(e, messageSource);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<UserAppErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<ErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.add(new ErrorDto(
                        new UserAppException(HttpStatus.BAD_REQUEST,
                                new ErrorCode(fieldError.getDefaultMessage()),
                                new ErrorSource("data/attributes/" + fieldError.getField())
                        ),
                        messageSource.getMessage(
                                Optional.ofNullable(fieldError.getDefaultMessage()).orElseThrow(UserAppException::getGeneralError),
                                Optional.ofNullable(fieldError.getRejectedValue()).map(Object::toString).map(List::of).map(List::toArray).orElse(new Object[]{}),
                                LocaleContextHolder.getLocale())
                ))
        );
        log.error(errors.toString());
        return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST)
                .body(new UserAppErrorResponse(errors));
    }
}
