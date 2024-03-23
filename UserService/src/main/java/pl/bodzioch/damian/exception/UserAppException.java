package pl.bodzioch.damian.exception;

import com.fasterxml.uuid.Generators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserAppException extends RuntimeException {

    public static final String REQUEST_ID_MDC_PARAM = "requestId";

    private final ErrorId errorId = new ErrorId(Generators.timeBasedEpochGenerator().generate());
    private final HttpStatus httpStatus;
    private final RequestId requestId = new RequestId(UUID.fromString(MDC.get(REQUEST_ID_MDC_PARAM)));
    private final List<ErrorData> errors;

    public static UserAppException getGeneralError() {
        return new UserAppException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                List.of(ErrorData.getGeneralErrorData())
        );
    }
}
