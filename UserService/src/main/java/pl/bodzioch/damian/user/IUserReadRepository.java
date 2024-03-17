package pl.bodzioch.damian.user;

import pl.bodzioch.damian.valueobject.Email;
import pl.bodzioch.damian.valueobject.UserId;
import pl.bodzioch.damian.valueobject.Username;

import java.util.Optional;

interface IUserReadRepository {

    Optional<User> findById(UserId userId);
    Optional<User> getByNaturalIds(Username username, Email email);
}
