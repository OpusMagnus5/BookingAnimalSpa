package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.valueobject.*;

public record ReadUserDto(
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
}
