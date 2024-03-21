package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.valueobject.ErrorSource;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public record UserAppErrorResponse(
        List<ErrorDto> errors
) implements Serializable {

    public UserAppErrorResponse(UserAppException e, String errorDetail) {
        this(
                List.of(new ErrorDto(
                        e.getErrorId().value().toString(),
                        e.getHttpStatus().getCode(),
                        e.getErrorCode().value(),
                        errorDetail,
                        Optional.ofNullable(e.getErrorSource()).map(ErrorSource::value).map(AppErrorSource::new).orElse(null),
                        new ErrorMetaData(e.getErrorTime().value(), e.getRequestId().value().toString())
                ))
        );
    }

    @Override
    public String toString() {
        return "UserAppErrorResponse{" +
                "errors=" + errors +
                '}';
    }
}
