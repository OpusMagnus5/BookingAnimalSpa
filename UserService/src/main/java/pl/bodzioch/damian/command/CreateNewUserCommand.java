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
                new Username(request.username()),
                new Password(request.password()),
                new Email(request.email()),
                new PhoneNumber(request.phoneNumber()),
                new FirstName(request.firstName()),
                new LastName(request.lastName()),
                new City(request.city()),
                new Country(Locale.of(request.country()))
        );
    }
}
