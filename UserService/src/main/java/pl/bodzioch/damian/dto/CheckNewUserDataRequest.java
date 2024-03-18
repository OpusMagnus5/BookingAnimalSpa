package pl.bodzioch.damian.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record CheckNewUserDataRequest( //TODO
        @Pattern(regexp = "[a-zA-Z0-9]", message = )
        String username,
        @Pattern(regexp = "\\S{9,}", message = )
        String password,
        @Email(message = )
        String email,
        @Pattern(regexp = "[0-9]{9}")
        String phoneNumber,
        @Pattern(regexp = "[a-zA-ZążęćłóńĄŻĘĆŁÓŃ]+", message = )
        String city,
        @Pattern(regexp = "[a-zA-ZążęćłóńĄŻĘĆŁÓŃ]+", message = )
        String country
) implements Serializable {
}
