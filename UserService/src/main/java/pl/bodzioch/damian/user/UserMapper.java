package pl.bodzioch.damian.user;

import pl.bodzioch.damian.dto.UserDto;

class UserMapper {

    static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.id(),
                user.username(),
                user.password(),
                user.email(),
                user.phoneNumber(),
                user.firstName(),
                user.lastName(),
                user.city(),
                user.country(),
                user.createTime(),
                user.modifyTime()
        );
    }
}
