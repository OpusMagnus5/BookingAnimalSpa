package pl.bodzioch.damian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReadAttributesDto(
        Long id,
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        String city,
        String country,
        String phoneNumber,
        Boolean active,
        LocalDateTime createTime,
        LocalDateTime modifyTime

) implements Serializable {

    public ReadAttributesDto(UserDto userDto) {
        this(
                userDto.id().value(),
                userDto.username().value(),
                userDto.email().value(),
                userDto.getPassword().isPresent() ? userDto.getPassword().get().value() : null,
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
