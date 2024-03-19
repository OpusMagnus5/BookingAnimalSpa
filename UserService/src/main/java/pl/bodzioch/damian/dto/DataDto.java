package pl.bodzioch.damian.dto;

public record DataDto(

        String type,
        AttributesDto attributes
) {

    public DataDto(User user) {
        this(
                user.getFullName()
        )
    }
}
