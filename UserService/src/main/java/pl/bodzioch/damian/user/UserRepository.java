package pl.bodzioch.damian.user;

import java.util.Optional;

interface UserRepository {

    Optional<UserEntity> getByUsername(String username);
}
