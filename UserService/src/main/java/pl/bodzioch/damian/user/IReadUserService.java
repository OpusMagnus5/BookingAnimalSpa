package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.command.FindUserByNatualIdCommand;
import pl.bodzioch.damian.command.ValidateNewUserDataCommand;
import pl.bodzioch.damian.dto.UserDto;

interface IReadUserService {

    UserDto handle(FindUserByIdCommand command);

    UserDto handle(FindUserByNatualIdCommand command);

    void handle(ValidateNewUserDataCommand command);
}
