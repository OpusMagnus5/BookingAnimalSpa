package pl.bodzioch.damian.user;

import pl.bodzioch.damian.dto.CreateUserDto;
import pl.bodzioch.damian.dto.ReadUserDto;

class UserMapper {

    static CreateUserDto mapToCreateUserDto(User user) {
        return new CreateUserDto(
                user.id(),
                user.username(),
                user.email(),
                user.phoneNumber(),
                user.city(),
                user.country(),
                user.createTime(),
                user.modifyTime()
        );
    }

    static ReadUserDto mapToReadUserDto(User user) {
        return new ReadUserDto(
                user.id(),
                user.username(),
                user.password(),
                user.email(),
                user.phoneNumber(),
                user.city(),
                user.country(),
                user.createTime(),
                user.modifyTime()
        );
    }
}
