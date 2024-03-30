package pl.bodzioch.damian.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record CreateNewUserRequest(

        @Valid
        @NotNull(message = "error.client.nullData")
        WriteDataDto data,
        @Valid
        RelationshipsDto relationships

) implements Serializable {
}
