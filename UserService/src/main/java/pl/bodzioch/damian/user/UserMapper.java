package pl.bodzioch.damian.user;

import pl.bodzioch.damian.dto.ReadUserDto;

class UserMapper {

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
