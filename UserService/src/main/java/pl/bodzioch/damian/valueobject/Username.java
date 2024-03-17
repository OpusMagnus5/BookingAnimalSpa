package pl.bodzioch.damian.valueobject;

public record Username(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Username username = (Username) o;

        return value.equals(username.value);
    }
}
