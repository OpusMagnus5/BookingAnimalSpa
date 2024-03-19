package pl.bodzioch.damian.dto;

public record CreateResourceResponse(

        DataDto data,
        RelationshipsDto relationships
) {

    public CreateResourceResponse(CreateUserDto createUserDto) {
        this(
                new DataDto()
        )
    }
}
