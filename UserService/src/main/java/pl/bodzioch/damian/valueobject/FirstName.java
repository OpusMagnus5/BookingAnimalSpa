package pl.bodzioch.damian.valueobject;

public record FirstName(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FirstName username = (FirstName) o;

        return value.equals(username.value);
    }
}
