package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.valueobject.ErrorData;
import pl.bodzioch.damian.valueobject.ErrorSource;
import pl.bodzioch.damian.valueobject.RequestId;

import java.util.Optional;

public record ErrorDto(
        String id,
        Integer status,
        String code,
        String detail,
        AppErrorSource source,
        ErrorMetaData meta
) {

    public ErrorDto(ErrorData e, String errorDetail, RequestId requestId) {
        this(
                e.errorId().value().toString(),
                e.httpStatus().getCode(),
                e.errorCode().value(),
                errorDetail,
                Optional.ofNullable(e.errorSource()).map(ErrorSource::value).map(AppErrorSource::new).orElse(null),
                new ErrorMetaData(e.errorTime().value(), requestId.value().toString())
        );
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", code='" + code + '\'' +
                ", detail='" + detail + '\'' +
                ", source=" + source +
                ", meta=" + meta +
                '}';
    }
}
