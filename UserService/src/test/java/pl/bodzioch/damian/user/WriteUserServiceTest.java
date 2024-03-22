package pl.bodzioch.damian.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.MDC;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.command.ValidateNewUserData;
import pl.bodzioch.damian.dto.UserDto;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.utils.UserEncoder;
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
        CreateNewUserCommand user1 = buildWriteCommand(username, new Email("email1"), new PhoneNumber("1"));
        ValidateNewUserData user2 = buildValidateCommand(username, new Email("email2"), new PhoneNumber("2"));
        assertDoesNotThrow(() -> writeUserService.handle(user1));

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
        CreateNewUserCommand user1Command = buildWriteCommand(new Username("user1"), email, new PhoneNumber("1"));
        ValidateNewUserData user2Command = buildValidateCommand(new Username("user2"), email, new PhoneNumber("2"));
        assertDoesNotThrow(() -> writeUserService.handle(user1Command));

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
        CreateNewUserCommand user1Command = buildWriteCommand(new Username("user1"), new Email("email1"), phoneNumber);
        ValidateNewUserData user2Command = buildValidateCommand(new Username("user2"), new Email("email2"), phoneNumber);
        assertDoesNotThrow(() -> writeUserService.handle(user1Command));

        UserAppException exception = assertThrows(UserAppException.class, () -> readUserService.handle(user2Command));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(new ErrorCode("error.client.phoneNumberExists"), exception.getErrorCode());
        assertEquals(new ErrorSource("data/attributes/phoneNumber"), exception.getErrorSource());
        assertEquals(exception.getParameters().size(), 1);
        assertTrue(exception.getParameters().contains(new ErrorDetailParameter(phoneNumber.value())));
    }

    @Test
    void Create_new_user_When_data_are_valid() {
        CreateNewUserCommand user1Command = buildWriteCommand(new Username("user1"), new Email("email1"), new PhoneNumber("1"));
        CreateNewUserCommand user2Command = buildWriteCommand(new Username("user2"), new Email("email2"), new PhoneNumber("2"));
        assertDoesNotThrow(() -> writeUserService.handle(user1Command));

        UserDto userDto = assertDoesNotThrow(() -> writeUserService.handle(user2Command));
        assertNotNull(userDto);
        assertEquals(user2Command.username(), userDto.username());
        assertTrue(UserEncoder.matches(user2Command.password().value(), userDto.password().value()));
        assertEquals(user2Command.email(), userDto.email());
        assertEquals(user2Command.phoneNumber(), userDto.phoneNumber());
        assertEquals(user2Command.firstName(), userDto.firstName());
        assertEquals(user2Command.lastName(), userDto.lastName());
        assertEquals(user2Command.city(), userDto.city());
        assertEquals(user2Command.country(), userDto.country());
    }

    private CreateNewUserCommand buildWriteCommand(Username user1, Email email1, PhoneNumber phoneNumber) {
        return new CreateNewUserCommand(
                user1,
                new Password("password"),
                email1,
                phoneNumber,
                new FirstName("firstName"),
                new LastName("lastName"),
                new City("city"),
                new Country(Locale.UK)
        );
    }

    private ValidateNewUserData buildValidateCommand(Username user, Email email, PhoneNumber phoneNumber) {
        return new ValidateNewUserData(
                user,
                email,
                phoneNumber,
                new SmsRequired(false)
        );
    }
}
