package pl.bodzioch.damian.dto;

import java.time.LocalDateTime;

public record AttributesDto(
        Long id,
        String username,
        String email,
        String city,
        String country,
        String phoneNumber,
        LocalDateTime createTime,
        LocalDateTime modifyTime

) {

    public AttributesDto(UserDto userDto) {
        this(
                userDto.id().value(),
                userDto.username().value(),
                userDto.email().value(),
                userDto.city().value(),
                userDto.country().value().getCountry(),
                userDto.phoneNumber().value(),
                userDto.createTime().value(),
                userDto.modifyTime().value()
        );
    }
}
