package pl.bodzioch.damian.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.MDC;
import pl.bodzioch.damian.command.ValidateNewUserData;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static pl.bodzioch.damian.exception.UserAppException.REQUEST_ID_MDC_PARAM;

class WriteUserServiceTest {

    private final UserConfiguration userConfiguration = new UserConfiguration(
            new TestUserWriteRepository(), new TestUserReadRepository()
    );
    private final IWriteUserService writeUserService = userConfiguration.writeUserService();
    private final IReadUserService readUserService = userConfiguration.readUserService();
    private static MockedStatic<MDC> mdcMocked;

    @BeforeAll
    static void init() {
        mdcMocked = Mockito.mockStatic(MDC.class);
        mdcMocked.when(() -> MDC.get(REQUEST_ID_MDC_PARAM)).thenReturn(UUID.randomUUID().toString());
    }

    @AfterAll
    static void cleanUp() {
        mdcMocked.close();
    }

    @Test
    void Create_new_user_When_user_with_username_exists() {
        Username username = new Username("user1");
        ValidateNewUserData user1 = buildCreateUserCommand(username, new Email("email1"), new PhoneNumber("1"));
        ValidateNewUserData user2 = buildCreateUserCommand(username, new Email("email2"), new PhoneNumber("2"));
        readUserService.handle(user1);

        UserAppException exception = assertThrows(UserAppException.class, () -> readUserService.handle(user2));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(new ErrorCode("error.client.usernameExists"), exception.getErrorCode());
        assertEquals(new ErrorSource("data/attributes/username"), exception.getErrorSource());
        assertEquals(exception.getParameters().size(), 1);
        assertTrue(exception.getParameters().contains(new ErrorDetailParameter(username.value())));
    }

    @Test
    void Create_new_user_When_user_with_email_exists() {
        Email email = new Email("email1");
        ValidateNewUserData user1Command = buildCreateUserCommand(new Username("user1"), email, new PhoneNumber("1"));
        ValidateNewUserData user2Command = buildCreateUserCommand(new Username("user2"), email, new PhoneNumber("2"));
        readUserService.handle(user1Command);

        UserAppException exception = assertThrows(UserAppException.class, () -> readUserService.handle(user2Command));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(new ErrorCode("error.client.emailExists"), exception.getErrorCode());
        assertEquals(new ErrorSource("data/attributes/email"), exception.getErrorSource());
        assertEquals(exception.getParameters().size(), 1);
        assertTrue(exception.getParameters().contains(new ErrorDetailParameter(email.value())));
    }

    @Test
    void Create_new_user_When_user_with_phone_exists() {
        PhoneNumber phoneNumber = new PhoneNumber("1");
        ValidateNewUserData user1Command = buildCreateUserCommand(new Username("user1"), new Email("email1"), phoneNumber);
        ValidateNewUserData user2Command = buildCreateUserCommand(new Username("user2"), new Email("email2"), phoneNumber);
        readUserService.handle(user1Command);

        UserAppException exception = assertThrows(UserAppException.class, () -> readUserService.handle(user2Command));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(new ErrorCode("error.client.phoneNumberExists"), exception.getErrorCode());
        assertEquals(new ErrorSource("data/attributes/phoneNumber"), exception.getErrorSource());
        assertEquals(exception.getParameters().size(), 1);
        assertTrue(exception.getParameters().contains(new ErrorDetailParameter(phoneNumber.value())));
    }
}
