package pl.bodzioch.damian.dto;

import java.time.LocalDateTime;

public record AttributesDto(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String city,
        String country,
        String phoneNumber,
        Boolean active,
        LocalDateTime createTime,
        LocalDateTime modifyTime

) {

    public AttributesDto(UserDto userDto) {
        this(
                userDto.id().value(),
                userDto.username().value(),
                userDto.email().value(),
                userDto.firstName().value(),
                userDto.lastName().value(),
                userDto.city().value(),
                userDto.country().value().getLanguage().toUpperCase(),
                userDto.phoneNumber().value(),
                userDto.active().value(),
                userDto.createTime().value(),
                userDto.modifyTime().value()
        );
    }
}
