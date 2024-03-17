package pl.bodzioch.damian.valueobject;

import java.util.Objects;

public record ErrorSource(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorSource that = (ErrorSource) o;

        return Objects.equals(value, that.value);
    }
}
