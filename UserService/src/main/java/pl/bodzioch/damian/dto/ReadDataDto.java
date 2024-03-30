package pl.bodzioch.damian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReadDataDto(

        String type,
        ReadAttributesDto attributes
) implements Serializable {

    public ReadDataDto(UserDto user) {
        this(
                UserDto.TYPE,
                new ReadAttributesDto(user)
        );
    }
}
