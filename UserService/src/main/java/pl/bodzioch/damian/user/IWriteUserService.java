package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.dto.UserDto;

interface IWriteUserService {

    UserDto handle(CreateNewUserCommand command);
}
