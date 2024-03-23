package pl.bodzioch.damian.valueobject;

import com.fasterxml.uuid.Generators;
import pl.bodzioch.damian.utils.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ErrorData(
        ErrorId errorId,
        HttpStatus httpStatus,
        ErrorCode errorCode,
        ErrorSource errorSource,
        List<ErrorDetailParameter> parameters,
        ErrorTime errorTime

) {
    public static final String GENERAL_ERROR_CODE = "error.server.general";

    public ErrorData(HttpStatus httpStatus, ErrorCode errorCode, ErrorSource errorSource, List<ErrorDetailParameter> parameters) {
        this(
                new ErrorId(Generators.timeBasedEpochGenerator().generate()),
                httpStatus,
                errorCode,
                errorSource,
                parameters,
                new ErrorTime(LocalDateTime.now())
        );
    }

    public static ErrorData getGeneralErrorData() {
        return new ErrorData(
                new ErrorId(Generators.timeBasedEpochGenerator().generate()),
                HttpStatus.INTERNAL_SERVER_ERROR,
                new ErrorCode(GENERAL_ERROR_CODE),
                null,
                Collections.emptyList(),
                new ErrorTime(LocalDateTime.now())
        );
    }
}
