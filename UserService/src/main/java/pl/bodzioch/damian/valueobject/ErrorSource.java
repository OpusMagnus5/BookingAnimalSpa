package pl.bodzioch.damian.valueobject;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public record ErrorSource(String value) {
}
