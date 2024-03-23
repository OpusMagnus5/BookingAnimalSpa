package pl.bodzioch.damian.dto;

import java.io.Serializable;
import java.util.List;

public record UserAppErrorResponse(
        List<ErrorDto> errors
) implements Serializable {

    @Override
    public String toString() {
        return "UserAppErrorResponse{" +
                "errors=" + errors +
                '}';
    }
}
