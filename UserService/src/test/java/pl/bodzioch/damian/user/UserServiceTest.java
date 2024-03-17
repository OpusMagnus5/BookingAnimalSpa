package pl.bodzioch.damian.user;

import org.junit.jupiter.api.Test;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserConfiguration userConfiguration = new UserConfiguration(new TestUserRepository());
    private final IUserService userService = userConfiguration.userService();

    @Test
    void Create_new_user_When_user_with_username_exists() {
        Username username1 = new Username("user1");
        Email email1 = new Email("email1");
        Email email2 = new Email("email2");
        User user1 = buildUser(username1, email1);
        User user2 = buildUser(username1, email2);

        userService.createNewUser(user1);

        UserAppException exception = assertThrows(UserAppException.class, () -> userService.createNewUser(user2));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(new ErrorCode("error.client.usernameExists"), exception.getErrorCode());
        assertEquals(new ErrorSource("data/attributes/username"), exception.getErrorSource());
        assertEquals(exception.getParameters().size(), 1);
        assertTrue(exception.getParameters().contains(new ErrorDetailParameter(username1.value())));
    }

    private User buildUser(Username username, Email email) {
        return new User(
                username,
                new Password("password"),
                email,
                new PhoneNumber("111111111"),
                new City("City"),
                new Country(Locale.UK)
        );
    }
}
