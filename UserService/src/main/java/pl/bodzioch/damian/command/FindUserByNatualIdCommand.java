package pl.bodzioch.damian.command;

import pl.bodzioch.damian.valueobject.Email;
import pl.bodzioch.damian.valueobject.PhoneNumber;
import pl.bodzioch.damian.valueobject.Username;

public record FindUserByNatualIdCommand(
        Username username,
        Email email,
        PhoneNumber phoneNumber
) {
}
