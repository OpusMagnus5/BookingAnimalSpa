package pl.bodzioch.damian.exception;

import com.fasterxml.uuid.Generators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserAppException extends RuntimeException {

    public static final String REQUEST_ID_MDC_PARAM = "requestId";
    public static final String GENERAL_ERROR_CODE = "error.server.general";

    private final ErrorId errorId = new ErrorId(Generators.timeBasedEpochGenerator().generate());
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;
    private final ErrorSource errorSource;
    private final ErrorTime errorTime = new ErrorTime(LocalDateTime.now());
    private final RequestId requestId = new RequestId(UUID.fromString(MDC.get(REQUEST_ID_MDC_PARAM)));
    private final List<ErrorDetailParameter> parameters;



    public static UserAppException getGeneralError() {
        return new UserAppException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                new ErrorCode(GENERAL_ERROR_CODE),
                null,
                Collections.emptyList()
        );
    }
}
