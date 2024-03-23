package pl.bodzioch.damian.util;

import org.apache.commons.text.RandomStringGenerator;
import pl.bodzioch.damian.valueobject.*;

import java.util.Locale;

public class TestDataGenerator {

    public static Username username() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'a','z'},{'A','Z'},{'0','9'}})
                .build();
        return new Username(generator.generate(30));
    }

    public static Password password() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'a','z'},{'A','Z'},{'0','9'}})
                .build();
        return new Password(generator.generate(64));
    }

    public static Email email() {
        RandomStringGenerator nameGenerator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'a','z'},{'A','Z'},{'0','9'}})
                .build();
        RandomStringGenerator domainGenerator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'a','z'}})
                .build();
        String name = nameGenerator.generate(12);
        String domain = domainGenerator.generate(8);
        return new Email(name + "@" + domain + ".com");
    }

    public static PhoneNumber phoneNumber() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'0','9'}})
                .build();
        return new PhoneNumber(generator.generate(9));
    }

    public static FirstName firstName() {
        return new FirstName(common());
    }

    public static LastName lastName() {
        return new LastName(common());
    }

    public static City city() {
        return new City(common());
    }

    private static String common() {
        RandomStringGenerator firstLetterGen = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'A','Z'}})
                .build();
        String firstLetter = firstLetterGen.generate(1);
        RandomStringGenerator remainingLetterGen = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'a','z'}})
                .build();
        return firstLetter + remainingLetterGen.generate(10);
    }

    public static Country country() {
        return new Country(Locale.getDefault());
    }

    public static SmsCode smsCode() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'0','9'}})
                .build();
        return new SmsCode(generator.generate(6));
    }
}
