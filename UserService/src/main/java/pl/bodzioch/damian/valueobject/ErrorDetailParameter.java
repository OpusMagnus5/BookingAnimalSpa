package pl.bodzioch.damian.valueobject;

import java.util.Objects;

public record ErrorDetailParameter(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorDetailParameter that = (ErrorDetailParameter) o;

        return Objects.equals(value, that.value);
    }
}
