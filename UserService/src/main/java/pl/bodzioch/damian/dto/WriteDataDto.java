package pl.bodzioch.damian.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record WriteDataDto(

        @NotEmpty(message = "error.client.typeEmpty")
        @Pattern(regexp = "user", message = "error.client.wrongType")
        String type,
        @Valid
        @NotNull(message = "error.client.attributesNull")
        WriteAttributesDto attributes
) {
}
