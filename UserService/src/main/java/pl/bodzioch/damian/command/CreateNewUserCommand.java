package pl.bodzioch.damian.command;

import pl.bodzioch.damian.valueobject.*;

public record CreateNewUserCommand(
        Username username,
        Password password,
        Email email,
        PhoneNumber phoneNumber,
        City city,
        Country country
) {
}
