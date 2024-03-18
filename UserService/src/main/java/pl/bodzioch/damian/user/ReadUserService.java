package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.command.ValidateNewUserData;
import pl.bodzioch.damian.dto.ReadUserDto;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class ReadUserService implements IReadUserService {

    private final IUserReadRepository userReadRepository;

    @Override
    public ReadUserDto handle(FindUserByIdCommand command) {
        User user = userReadRepository.findById(command.userId())
                .orElseThrow(() -> buildUserNotExistByIdException(command.userId()));
        return UserMapper.mapToReadUserDto(user);
    }

    @Override
    public void handle(ValidateNewUserData command) {
        Optional<User> user = userReadRepository.getByNaturalIds(command.username(), command.email(), command.phoneNumber());
        user.ifPresent(entity -> checkIncorrectParameter(command, entity));
        sendSmsToPhoneNumber();
    }

    private void checkIncorrectParameter(ValidateNewUserData command, User exists) {
        if (command.username().equals(exists.username())) {
            throwUserExistsByUsernameException(command.username());
        } else if (command.email().equals(exists.email())) {
            throwUserExistsByEmailException(command.email());
        } else if (command.phoneNumber().equals(exists.phoneNumber())) {
            throwUserExistsByPhoneException(command.phoneNumber());
        }
    }

    private void sendSmsToPhoneNumber() {
        //TODO
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

    private void throwUserExistsByPhoneException(PhoneNumber phoneNumber) {
        throw new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.phoneNumberExists"),
                new ErrorSource("data/attributes/phoneNumber"),
                List.of(new ErrorDetailParameter(phoneNumber.value()))
        );
    }

    private UserAppException buildUserNotExistByIdException(UserId id) {
        return new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.userByIdNotExists"),
                new ErrorSource("data/attributes/id"),
                List.of(new ErrorDetailParameter(id.value().toString()))
        );
    }
}
