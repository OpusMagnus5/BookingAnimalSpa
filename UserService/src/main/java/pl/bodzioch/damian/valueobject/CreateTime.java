package pl.bodzioch.damian.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateTime(LocalDateTime value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateTime that = (CreateTime) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
