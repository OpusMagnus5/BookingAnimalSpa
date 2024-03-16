package pl.bodzioch.damian.dto;

import lombok.ToString;

import java.io.Serializable;

@ToString
public record AppErrorSource(
        String pointer
) implements Serializable {
}
