package pl.bodzioch.damian.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bodzioch.damian.dto.ErrorDto;
import pl.bodzioch.damian.dto.UserAppErrorResponse;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.valueobject.ErrorDetailParameter;

import java.util.ArrayList;
import java.util.List;

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
        List<ErrorDto> errorsDto = new ArrayList<>();
        exception.getErrors().forEach(e -> {
            String errorCode = e.errorCode().value();
            Object[] messageParams = e.parameters().stream().map(ErrorDetailParameter::value).toArray();
            String errorDetail = messageSource.getMessage(errorCode, messageParams, LocaleContextHolder.getLocale());
            errorsDto.add(new ErrorDto(e, errorDetail, exception.getRequestId()));
        });

        return ResponseEntity.status(exception.getHttpStatus().getCode())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(new UserAppErrorResponse(errorsDto));
    }
}
