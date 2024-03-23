package pl.bodzioch.damian.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

public class JsonToString {

    public static String getResponseJson(String fileName) throws IOException {
        return Files.readString(
                new ClassPathResource("response/" + fileName + ".json")
                        .getFile()
                        .toPath()
        );
    }
}
