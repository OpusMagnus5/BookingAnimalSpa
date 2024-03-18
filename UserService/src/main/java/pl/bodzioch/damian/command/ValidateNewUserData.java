package pl.bodzioch.damian.command;

import pl.bodzioch.damian.valueobject.*;

public record ValidateNewUserData(
        Username username,
        Email email,
        PhoneNumber phoneNumber
) {
}
