package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.valueobject.ErrorSource;

import java.io.Serializable;
import java.util.Optional;

public record UserAppErrorResponse(
        String id,
        Integer status,
        String code,
        String detail,
        AppErrorSource source,
        ErrorMetaData meta
) implements Serializable {

    public UserAppErrorResponse(UserAppException e, String errorDetail) {
        this(
                e.getErrorId().value().toString(),
                e.getHttpStatus().getCode(),
                e.getErrorCode().value(),
                errorDetail,
                Optional.ofNullable(e.getErrorSource()).map(ErrorSource::value).map(AppErrorSource::new).orElse(null),
                new ErrorMetaData(e.getErrorTime().value(), e.getRequestId().value().toString())
        );
    }

    @Override
    public String toString() {
        return "UserAppErrorResponse{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", code='" + code + '\'' +
                ", detail='" + detail + '\'' +
                ", source=" + source +
                ", meta=" + meta +
                '}';
    }
}
