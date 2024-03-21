package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.valueobject.*;

public record UserDto(
        UserId id,
        Username username,
        Password password,
        Email email,
        PhoneNumber phoneNumber,
        City city,
        Country country,
        CreateTime createTime,
        ModifyTime modifyTime
) {
    public static final String TYPE = "user";
}
