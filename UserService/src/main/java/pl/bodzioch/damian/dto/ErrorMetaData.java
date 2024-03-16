package pl.bodzioch.damian.dto;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public record ErrorMetaData(
        LocalDateTime timestamp,
        String requestId
) {
}
