package pl.bodzioch.damian.valueobject;

import java.util.Objects;

public record Email(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return Objects.equals(value, email.value);
    }
}
