package pl.bodzioch.damian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.slf4j.MDC;

import java.io.Serializable;

import static pl.bodzioch.damian.exception.UserAppException.REQUEST_ID_MDC_PARAM;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class MetaData implements Serializable {

    private final String requestId = MDC.get(REQUEST_ID_MDC_PARAM);
}
