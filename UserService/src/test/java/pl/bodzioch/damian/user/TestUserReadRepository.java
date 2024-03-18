package pl.bodzioch.damian.user;

import pl.bodzioch.damian.valueobject.Email;
import pl.bodzioch.damian.valueobject.PhoneNumber;
import pl.bodzioch.damian.valueobject.UserId;
import pl.bodzioch.damian.valueobject.Username;

import java.util.Optional;

class TestUserReadRepository extends TestUserRepository implements IUserReadRepository {

    @Override
    public Optional<User> getByNaturalIds(Username username, Email email, PhoneNumber phoneNumber) {
        return repository.values().stream()
                .filter(user -> username.value().equals(user.getUsername())
                        || email.value().equals(user.getEmail())
                        || phoneNumber.value().equals(user.getPhoneNumber()))
                .findAny()
                .map(User::new);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return Optional.ofNullable(repository.get(userId.value()))
                .map(User::new);
    }
}
