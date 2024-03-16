package pl.bodzioch.damian.dto;

import lombok.ToString;
import pl.bodzioch.damian.exception.UserAppException;

import java.io.Serializable;

@ToString
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
                new AppErrorSource(e.getErrorSource().value()),
                new ErrorMetaData(e.getErrorTime().value(), e.getRequestId().value().toString())
        );
    }
}
