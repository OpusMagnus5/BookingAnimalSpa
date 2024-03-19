package pl.bodzioch.damian.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record CreateNewUserRequest(
        @Pattern(regexp = "[a-zA-Z0-9]", message = "error.client.incorrectUsername")
        String username,
        @Pattern(regexp = "\\S{9,}", message = "error.client.incorrectPassword")
        String password,
        @Email(message = "error.client.incorrectEmail")
        String email,
        @Pattern(regexp = "\\d{9}", message = "error.client.incorrectPhoneNumber")
        String phoneNumber,
        @Pattern(regexp = "[a-zA-ZążęćłóńĄŻĘĆŁÓŃ ]+", message = "error.client.incorrectCity")
        String city,
        @Pattern(regexp = "[a-zA-ZążęćłóńĄŻĘĆŁÓŃ ]+", message = "error.client.incorrectCountry")
        String country,
        @Pattern(regexp = "\\d{6}")
        String sms
) implements Serializable {
}
