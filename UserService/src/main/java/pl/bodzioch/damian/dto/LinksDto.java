package pl.bodzioch.damian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LinksDto(
        String self
) implements Serializable {
}
