package pl.bodzioch.damian.valueobject;

import java.util.Objects;

public record SmsCode(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsCode smsCode = (SmsCode) o;

        return Objects.equals(value, smsCode.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
