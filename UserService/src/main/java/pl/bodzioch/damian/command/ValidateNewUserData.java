package pl.bodzioch.damian.command;

import pl.bodzioch.damian.dto.CheckNewUserDataRequest;
import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.valueobject.*;

public record ValidateNewUserData(
        Username username,
        Email email,
        PhoneNumber phoneNumber,
        SmsRequired smsRequired
) {

    public ValidateNewUserData(CheckNewUserDataRequest request) {
        this(
                new Username(request.username()),
                new Email(request.email()),
                new PhoneNumber(request.phoneNumber()),
                new SmsRequired(true)
        );
    }

    public ValidateNewUserData(CreateNewUserRequest request) {
        this(
                new Username(request.username()),
                new Email(request.email()),
                new PhoneNumber(request.phoneNumber()),
                new SmsRequired(false)
        );
    }
}
