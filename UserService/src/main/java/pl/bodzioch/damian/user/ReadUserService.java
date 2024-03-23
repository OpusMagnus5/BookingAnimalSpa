package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.command.ValidateNewUserData;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class ReadUserService implements IReadUserService {

    private final IUserReadRepository userReadRepository;

    @Override
    public User handle(FindUserByIdCommand command) {
        return userReadRepository.findById(command.userId())
                .orElseThrow(() -> new UserAppException(
                        HttpStatus.BAD_REQUEST,
                        List.of(buildUserNotExistByIdException(command.userId())))
                );
    }

    @Override
    public void handle(ValidateNewUserData command) {
        Optional<User> user = userReadRepository.getByNaturalIds(command.username(), command.email(), command.phoneNumber());
        user.ifPresent(entity -> entity.checkIncorrectParameter(command));
        if (command.smsRequired().value()) {
            sendSmsToPhoneNumber();
        }
    }

    private void sendSmsToPhoneNumber() {
        //TODO
    }

    private ErrorData buildUserNotExistByIdException(UserId id) {
        return new ErrorData(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.userByIdNotExists"),
                new ErrorSource("data/attributes/id"),
                List.of(new ErrorDetailParameter(id.value().toString()))
        );
    }
}
