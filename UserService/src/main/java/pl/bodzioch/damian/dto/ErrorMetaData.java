package pl.bodzioch.damian.dto;

import java.time.LocalDateTime;

public record ErrorMetaData(
        LocalDateTime timestamp,
        String requestId
) {

    @Override
    public String toString() {
        return "ErrorMetaData{" +
                "timestamp=" + timestamp +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
