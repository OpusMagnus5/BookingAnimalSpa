package pl.bodzioch.damian.dto;

public record CreateResourceResponse(

        DataDto data,
        RelationshipsDto relationships
) {

    public CreateResourceResponse(UserDto userDto) {
        this(
                new DataDto(userDto),
                null
        );
    }
}
