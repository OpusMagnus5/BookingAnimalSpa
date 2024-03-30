package pl.bodzioch.damian.dto;

public record ReadDataDto(

        String type,
        ReadAttributesDto attributes
) {

    public ReadDataDto(UserDto user) {
        this(
                UserDto.TYPE,
                new ReadAttributesDto(user)
        );
    }
}
