package pl.bodzioch.damian.user;

import pl.bodzioch.damian.valueobject.Email;
import pl.bodzioch.damian.valueobject.Username;

import java.util.Optional;

interface IUserRepository {

    Optional<User> getByNaturalIds(Username username, Email email);

    User createNew(UserEntity user);
}
