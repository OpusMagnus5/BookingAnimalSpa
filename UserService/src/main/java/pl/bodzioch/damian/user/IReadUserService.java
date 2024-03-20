package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.command.ValidateNewUserData;

interface IReadUserService {

    User handle(FindUserByIdCommand command);
    void handle(ValidateNewUserData command);
}
