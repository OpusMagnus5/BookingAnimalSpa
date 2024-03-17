package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.dto.CreateUserDto;

interface IWriteUserService {

    CreateUserDto handle(CreateNewUserCommand command);
}
