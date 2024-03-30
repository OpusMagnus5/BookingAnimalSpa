package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.dto.UserDto;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class WriteUserService implements IWriteUserService {

    private final IUserWriteRepository userWriteRepository;

    @Override
    public UserDto handle(CreateNewUserCommand command) {
        User user = userWriteRepository.createNew(new UserEntity(command));
        return UserMapper.mapToUserWithoutPassword(user);
        //TODO wyslac maila
    }
}
