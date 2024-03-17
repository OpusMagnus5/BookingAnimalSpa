package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.valueobject.*;

public record CreateUserDto(
        UserId id,
        Username username,
        Email email,
        PhoneNumber phoneNumber,
        City city,
        Country country,
        CreateTime createTime,
        ModifyTime modifyTime
) {
}
