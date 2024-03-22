package pl.bodzioch.damian.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record CheckNewUserDataRequest(
        @NotEmpty(message = "error.client.usernameEmpty")
        @Pattern(regexp = "[a-zA-Z0-9]+", message = "error.client.incorrectUsername")
        String username,
        @NotEmpty(message = "error.client.passwordEmpty")
        @Pattern(regexp = "\\S{9,}", message = "error.client.incorrectPassword")
        String password,
        @NotEmpty(message = "error.client.emailEmpty")
        @Email(message = "error.client.incorrectEmail")
        String email,
        @NotEmpty(message = "error.client.phoneNumberEmpty")
        @Pattern(regexp = "\\d{9}", message = "error.client.incorrectPhoneNumber")
        String phoneNumber,
        @NotEmpty(message = "error.client.firstNameEmpty")
        @Pattern(regexp = "[a-zA-ZążęćłóńĄŻĘĆŁÓŃ]+", message = "error.client.incorrectFirstName")
        String firstName,
        @NotEmpty(message = "error.client.lastNameEmpty")
        @Pattern(regexp = "[a-zA-ZążęćłóńĄŻĘĆŁÓŃ]+", message = "error.client.incorrectLastName")
        String lastName,
        @NotEmpty(message = "error.client.cityEmpty")
        @Pattern(regexp = "[a-zA-ZążęćłóńĄŻĘĆŁÓŃ ]+", message = "error.client.incorrectCity")
        String city,
        @NotEmpty(message = "error.client.countryEmpty")
        @Pattern(regexp = "[A-Z]{2,3}", message = "error.client.incorrectCountry")
        String country
) implements Serializable {
}
