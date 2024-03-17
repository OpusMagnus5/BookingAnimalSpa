package pl.bodzioch.damian.command;

import pl.bodzioch.damian.valueobject.UserId;

public record FindUserByIdCommand(
        UserId userId
) {
}
