package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.bodzioch.damian.command.CreateNewUserCommand;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class WriteUserService implements IWriteUserService {

    private final IUserWriteRepository userWriteRepository;

    User handle(CreateNewUserCommand command) {
        return userWriteRepository.createNew(new UserEntity(command));
        //TODO wyslac maila
    }
}
