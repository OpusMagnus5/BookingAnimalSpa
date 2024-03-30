package pl.bodzioch.damian.dto;

import pl.bodzioch.damian.utils.ResourceLinkGenerator;

public record CreateResourceResponse(
        ReadDataDto data,
        LinksDto links
) {

    public CreateResourceResponse(UserDto userDto) {
        this(
                new ReadDataDto(userDto),
                new LinksDto(ResourceLinkGenerator.userResource(userDto.id()))
        );
    }
}
