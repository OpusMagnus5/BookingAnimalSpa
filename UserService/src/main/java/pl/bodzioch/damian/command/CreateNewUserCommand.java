package pl.bodzioch.damian.command;

import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.valueobject.*;

import java.util.Locale;

public record CreateNewUserCommand(
        Username username,
        Password password,
        Email email,
        PhoneNumber phoneNumber,
        FirstName firstName,
        LastName lastName,
        City city,
        Country country
) {

    public CreateNewUserCommand(CreateNewUserRequest request) {
        this(
                new Username(request.data().attributes().username()),
                new Password(request.data().attributes().password()),
                new Email(request.data().attributes().email()),
                new PhoneNumber(request.data().attributes().phoneNumber()),
                new FirstName(request.data().attributes().firstName()),
                new LastName(request.data().attributes().lastName()),
                new City(request.data().attributes().city()),
                new Country(Locale.of(request.data().attributes().country()))
        );
    }
}
