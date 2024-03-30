package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.command.ValidateNewUserDataCommand;

interface IReadUserService {

    User handle(FindUserByIdCommand command);
    void handle(ValidateNewUserDataCommand command);
}
