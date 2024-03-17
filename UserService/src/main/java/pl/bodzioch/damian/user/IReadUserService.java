package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.dto.ReadUserDto;

interface IReadUserService {
    ReadUserDto handle(FindUserByIdCommand command);
}
