package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.dto.ReadUserDto;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class ReadUserService implements IReadUserService {

    private final IUserReadRepository userReadRepository;

    @Override
    public ReadUserDto handle(FindUserByIdCommand command) {
        User user = userReadRepository.findById(command.userId())
                .orElseThrow(() -> buildUserNotExistByIdException(command.userId()));
        return UserMapper.mapToReadUserDto(user);
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
