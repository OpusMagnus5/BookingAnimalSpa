package pl.bodzioch.damian.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpStatus {
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500);

    private final Integer code;
}
