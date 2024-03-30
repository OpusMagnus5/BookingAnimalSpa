package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.command.FindUserByNatualIdCommand;
import pl.bodzioch.damian.command.ValidateNewUserDataCommand;
import pl.bodzioch.damian.dto.UserDto;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class ReadUserService implements IReadUserService {

    private final IUserReadRepository userReadRepository;

    @Override
    public UserDto handle(FindUserByIdCommand command) {
        return userReadRepository.findById(command.userId())
                .map(UserMapper::mapToUserWithoutPassword)
                .orElseThrow(() -> new UserAppException(
                        HttpStatus.BAD_REQUEST,
                        List.of(buildUserNotExistByIdException(command.userId())))
                );
    }

    @Override
    public UserDto handle(FindUserByNatualIdCommand command) {
        return userReadRepository.getByNaturalIds(command.username(), command.email(), command.phoneNumber())
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new UserAppException(
                        HttpStatus.BAD_REQUEST,
                        List.of(buildUserNotExistByNaturalId(command))
                ));
    }

    @Override
    public void handle(ValidateNewUserDataCommand command) {
        Optional<User> user = userReadRepository.getByNaturalIds(command.username(), command.email(), command.phoneNumber());
        user.ifPresent(entity -> entity.checkIncorrectParameter(command));
    }

    private ErrorData buildUserNotExistByIdException(UserId id) {
        return new ErrorData(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.userByIdNotExists"),
                new ErrorSource("data.attributes.id"),
                List.of(new ErrorDetailParameter(id.value().toString()))
        );
    }

    private ErrorData buildUserNotExistByNaturalId(FindUserByNatualIdCommand command) {
        String errorCode;
        String errorSource;
        ErrorDetailParameter errorDetailParameter;
        if (StringUtils.isNotBlank(command.username().value())) {
            errorSource = "username";
            errorCode = "error.client.userByUsernameNotExists";
            errorDetailParameter = new ErrorDetailParameter(command.username().value());
        } else if (StringUtils.isNotBlank(command.email().value())) {
            errorSource = "email";
            errorCode = "error.client.userByEmailNotExists";
            errorDetailParameter = new ErrorDetailParameter(command.email().value());
        } else {
            errorSource = "phoneNumber";
            errorCode = "error.client.userByPhoneNumberNotExists";
            errorDetailParameter = new ErrorDetailParameter(command.phoneNumber().value());
        }

        return new ErrorData(
                HttpStatus.BAD_REQUEST,
                new ErrorCode(errorCode),
                new ErrorSource(errorSource),
                List.of(errorDetailParameter)
        );
    }
}
