package pl.bodzioch.damian.user;

import org.apache.commons.text.RandomStringGenerator;

public class TestDataGenerator {

    public static String username() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .filteredBy(Character::isAlphabetic)
                .filteredBy(Character::isDigit)
                .build();
        return generator.generate(30);
    }

    public static String password() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .filteredBy(codePoint -> !Character.isWhitespace(codePoint))
                .build();
        return generator.generate(64);
    }

    public static String email() {
        RandomStringGenerator nameGenerator = new RandomStringGenerator.Builder()
                .filteredBy(Character::isAlphabetic)
                .filteredBy(Character::isDigit)
                .build();
        RandomStringGenerator domainGenerator = new RandomStringGenerator.Builder()
                .filteredBy(Character::isAlphabetic)
                .build();
        String name = nameGenerator.generate(64);
        String domain = domainGenerator.generate(64);
        return name + "@" + domain + ".com";
    }

    public static String phoneNumber() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .filteredBy(Character::isDigit)
                .build();
        return generator.generate(9);
    }

    public static String firstName() {

    }
}
