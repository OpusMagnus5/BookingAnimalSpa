package pl.bodzioch.damian.valueobject;

public record LastName(String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastName username = (LastName) o;

        return value.equals(username.value);
    }
}
