package pl.bodzioch.damian.valueobject;

public record UserId(Long value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserId userId = (UserId) o;

        return value.equals(userId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
