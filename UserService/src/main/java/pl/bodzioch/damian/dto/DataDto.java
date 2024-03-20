package pl.bodzioch.damian.dto;

public record DataDto(

        String type,
        AttributesDto attributes
) {

    public DataDto(UserDto user) {
        this(
                UserDto.TYPE,
                new AttributesDto(user)
        );
    }
}
