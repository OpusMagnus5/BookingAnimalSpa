package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.ErrorCode;
import pl.bodzioch.damian.valueobject.ErrorDetailParameter;
import pl.bodzioch.damian.valueobject.ErrorSource;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class UserService {

    private final UserRepository userRepository;

    void createNewUser(User user) {
        Optional<UserEntity> userEntity = userRepository.getByUsername(user.username().value());
        userEntity.ifPresent(this::throwUserExistsException);
    }

    private void throwUserExistsException(UserEntity userEntity) {
        throw new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.usernameExists"),
                new ErrorSource("data/attributes/username"),
                List.of(new ErrorDetailParameter(userEntity.getUsername()))
        );
    }
}
