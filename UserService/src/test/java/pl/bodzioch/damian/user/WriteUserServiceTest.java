package pl.bodzioch.damian.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.MDC;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.command.FindUserByIdCommand;
import pl.bodzioch.damian.dto.CreateUserDto;
import pl.bodzioch.damian.dto.ReadUserDto;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.Locale;
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
        Email email1 = new Email("email1");
        Email email2 = new Email("email2");
        CreateNewUserCommand user1 = buildCreateUserCommand(username, email1);
        CreateNewUserCommand user2 = buildCreateUserCommand(username, email2);
        writeUserService.handle(user1);

        UserAppException exception = assertThrows(UserAppException.class, () -> writeUserService.handle(user2));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(new ErrorCode("error.client.usernameExists"), exception.getErrorCode());
        assertEquals(new ErrorSource("data/attributes/username"), exception.getErrorSource());
        assertEquals(exception.getParameters().size(), 1);
        assertTrue(exception.getParameters().contains(new ErrorDetailParameter(username.value())));
    }

    @Test
    void Create_new_user_When_user_with_email_exists() {
        Username username1 = new Username("user1");
        Username username2 = new Username("user2");
        Email email = new Email("email1");
        CreateNewUserCommand user1Command = buildCreateUserCommand(username1, email);
        CreateNewUserCommand user2Command = buildCreateUserCommand(username2, email);
        writeUserService.handle(user1Command);

        UserAppException exception = assertThrows(UserAppException.class, () -> writeUserService.handle(user2Command));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(new ErrorCode("error.client.emailExists"), exception.getErrorCode());
        assertEquals(new ErrorSource("data/attributes/email"), exception.getErrorSource());
        assertEquals(exception.getParameters().size(), 1);
        assertTrue(exception.getParameters().contains(new ErrorDetailParameter(email.value())));
    }

    @Test
    void Create_new_user_When_user_is_not_exists() {
        CreateNewUserCommand createUserCommand = buildCreateUserCommand(new Username("user"), new Email("email"));
        CreateUserDto createdUser = assertDoesNotThrow(() -> writeUserService.handle(createUserCommand));
        assertNotNull(writeUserService);

        FindUserByIdCommand findUserCommand = new FindUserByIdCommand(createdUser.id());
        ReadUserDto readUser = readUserService.handle(findUserCommand);
        assertNotNull(readUser);
        assertEquals(createdUser.id(), readUser.id());
    }

    private CreateNewUserCommand buildCreateUserCommand(Username username, Email email) {
        return new CreateNewUserCommand(
                username,
                new Password("password"),
                email,
                new PhoneNumber("111111111"),
                new City("City"),
                new Country(Locale.UK)
        );
    }
}
