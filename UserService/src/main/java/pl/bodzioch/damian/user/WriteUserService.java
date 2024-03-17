package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.dto.CreateUserDto;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class WriteUserService implements IWriteUserService {

    private final IUserWriteRepository userWriteRepository;
    private final IUserReadRepository userReadRepository;

    @Override
    public CreateUserDto handle(CreateNewUserCommand command) {
        Optional<User> user = userReadRepository.getByNaturalIds(command.username(), command.email());
        user.ifPresent(entity -> checkIncorrectParameter(command, entity));
        User createdUser = userWriteRepository.createNew(UserEntity.of(command));
        return UserMapper.mapToCreateUserDto(createdUser);
    }

    private void checkIncorrectParameter(CreateNewUserCommand command, User exists) {
        if (command.username().equals(exists.username())) {
            throwUserExistsByUsernameException(command.username());
        } else if (command.email().equals(exists.email())) {
            throwUserExistsByEmailException(command.email());
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
                new ErrorCode("error.client.emailExists"),
                new ErrorSource("data/attributes/email"),
                List.of(new ErrorDetailParameter(email.value()))
        );
    }
}
