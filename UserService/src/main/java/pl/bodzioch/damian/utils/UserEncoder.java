package pl.bodzioch.damian.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bodzioch.damian.valueobject.Password;

public class UserEncoder {

    private static final PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public static String encodePassword(Password password) {
        return bcryptEncoder.encode(password.value());
    }
}
