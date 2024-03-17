package pl.bodzioch.damian.valueobject;

import java.util.Objects;

public record ErrorCode(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorCode errorCode = (ErrorCode) o;

        return Objects.equals(value, errorCode.value);
    }
}
