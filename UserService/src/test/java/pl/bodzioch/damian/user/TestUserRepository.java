package pl.bodzioch.damian.user;

import pl.bodzioch.damian.valueobject.Email;
import pl.bodzioch.damian.valueobject.Username;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class TestUserRepository implements IUserRepository {

    private final ConcurrentHashMap<Long, UserEntity> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<User> getByNaturalIds(Username username, Email email) {
        return repository.values().stream()
                .filter(user -> username.equals(user.getUsername())) //TODO fix
                .findAny()
                .map(User::new);
    }

    @Override
    public User createNew(UserEntity user) {
        repository.put(user.getId(), user);
        UserEntity userEntity = repository.get(user.getId());
        return new User(userEntity);
    }
}
