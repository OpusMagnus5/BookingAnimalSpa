package pl.bodzioch.damian.user;

interface UserRepository {

    UserEntity getById(Long userId);
}
