package pl.bodzioch.damian.command;

import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.valueobject.*;

public record ValidateNewUserDataCommand(
        Username username,
        Email email,
        PhoneNumber phoneNumber
) {

    public ValidateNewUserDataCommand(CreateNewUserRequest request) {
        this(
                new Username(request.data().attributes().username()),
                new Email(request.data().attributes().email()),
                new PhoneNumber(request.data().attributes().phoneNumber())
        );
    }
}
