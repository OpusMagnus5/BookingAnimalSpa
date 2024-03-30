package pl.bodzioch.damian.dto;

import jakarta.annotation.Nullable;
import pl.bodzioch.damian.valueobject.*;

import java.util.Optional;

public record UserDto(
        UserId id,
        Username username,
        @Nullable
        Password password,
        Email email,
        PhoneNumber phoneNumber,
        FirstName firstName,
        LastName lastName,
        City city,
        Country country,
        IsActive active,
        CreateTime createTime,
        ModifyTime modifyTime
) {
    public static final String TYPE = "user";

    public Optional<Password> getPassword() {
        return Optional.ofNullable(password);
    }
}
