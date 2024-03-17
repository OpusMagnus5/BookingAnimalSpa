package pl.bodzioch.damian.dto;

import java.io.Serializable;

public record AppErrorSource(
        String pointer
) implements Serializable {

    @Override
    public String toString() {
        return "AppErrorSource{" +
                "pointer='" + pointer + '\'' +
                '}';
    }
}
