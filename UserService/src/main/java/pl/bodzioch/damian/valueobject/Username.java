package pl.bodzioch.damian.valueobject;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public record Username(String value) {
}
