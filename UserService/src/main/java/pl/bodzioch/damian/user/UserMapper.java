package pl.bodzioch.damian.user;

import pl.bodzioch.damian.dto.UserDto;

public class UserMapper {

    static UserDto mapToUserDto(User user) {
        return new UserDto(
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
}
