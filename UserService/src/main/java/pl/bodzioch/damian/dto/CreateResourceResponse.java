package pl.bodzioch.damian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.bodzioch.damian.utils.ResourceLinkGenerator;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateResourceResponse(
        ReadDataDto data,
        LinksDto links,
        MetaData meta

) implements Serializable {

    public CreateResourceResponse(UserDto userDto) {
        this(
                new ReadDataDto(userDto),
                new LinksDto(ResourceLinkGenerator.userResource(userDto.id())),
                new MetaData()
        );
    }
}
