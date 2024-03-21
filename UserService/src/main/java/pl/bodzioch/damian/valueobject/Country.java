package pl.bodzioch.damian.valueobject;

import java.util.Locale;
import java.util.Objects;

public record Country(Locale value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return Objects.equals(value.getLanguage(), country.value.getLanguage());
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
