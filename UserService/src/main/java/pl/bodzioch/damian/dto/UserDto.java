package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.valueobject.*;

public record UserDto(
        UserId id,
        Username username,
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
}
