package pl.bodzioch.damian.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bodzioch.damian.dto.ErrorDto;
import pl.bodzioch.damian.dto.UserAppErrorResponse;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.ErrorCode;
import pl.bodzioch.damian.valueobject.ErrorData;
import pl.bodzioch.damian.valueobject.ErrorSource;
import pl.bodzioch.damian.valueobject.RequestId;

import java.util.*;

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
        e.getBindingResult().getFieldErrors().forEach(fieldError -> errors.add(
                        new ErrorDto(
                                getErrorData(fieldError),
                                getMessage(fieldError),
                                new RequestId(UUID.fromString(MDC.get(UserAppException.REQUEST_ID_MDC_PARAM)))
                        )));
        log.error(errors.toString());
        return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(new UserAppErrorResponse(errors));
    }

    private ErrorData getErrorData(FieldError fieldError) {
        return new ErrorData(
                HttpStatus.BAD_REQUEST,
                new ErrorCode(fieldError.getDefaultMessage()),
                new ErrorSource(fieldError.getField()),
                Collections.emptyList()
        );
    }

    private String getMessage(FieldError fieldError) {
        return messageSource.getMessage(
                Optional.ofNullable(fieldError.getDefaultMessage()).orElseThrow(UserAppException::getGeneralError),
                Optional.ofNullable(fieldError.getRejectedValue()).map(Object::toString).map(List::of).map(List::toArray).orElse(new Object[]{}),
                LocaleContextHolder.getLocale());
    }
}
