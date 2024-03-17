package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public User createNewUser(User user) {
        Optional<User> userEntity = userRepository.getByNaturalIds(user.username(), user.email());
        userEntity.ifPresent(entity -> checkIncorrectParameter(user, entity));
        return userRepository.createNew(UserEntity.of(user));
    }

    private void checkIncorrectParameter(User toCreate, User exists) {
        if (toCreate.username().equals(exists.username())) {
            throwUserExistsByUsernameException(toCreate.username());
        } else if (toCreate.email().equals(exists.email())) {
            throwUserExistsByEmailException(toCreate.email());
        }
    }

    private void throwUserExistsByUsernameException(Username username) {
        throw new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.usernameExists"),
                new ErrorSource("data/attributes/username"),
                List.of(new ErrorDetailParameter(username.value()))
        );
    }

    private void throwUserExistsByEmailException(Email email) {
        throw new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.usernameExists"),
                new ErrorSource("data/attributes/username"),
                List.of(new ErrorDetailParameter(email.value()))
        );
    }
}
