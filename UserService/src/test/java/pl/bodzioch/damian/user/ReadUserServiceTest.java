package pl.bodzioch.damian.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.MDC;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.command.ValidateNewUserDataCommand;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static pl.bodzioch.damian.exception.UserAppException.REQUEST_ID_MDC_PARAM;
import static pl.bodzioch.damian.util.TestDataGenerator.*;

class ReadUserServiceTest {

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
    void Validate_new_user_When_user_with_all_unique_data_exists() {
        Username username = username();
        Email email = email();
        PhoneNumber phone = phoneNumber();
        CreateNewUserCommand user1 = buildWriteCommand(username, email, phone);
        ValidateNewUserDataCommand user2 = buildValidateCommand(username, email, phone);
        assertDoesNotThrow(() -> writeUserService.handle(user1));

        UserAppException exception = assertThrows(UserAppException.class, () -> readUserService.handle(user2));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertNotNull(exception.getErrorId());
        List<ErrorData> errors = exception.getErrors();
        assertEquals(3, errors.size());

        ErrorData firstError = errors.getFirst();
        assertEquals(HttpStatus.BAD_REQUEST, firstError.httpStatus());
        assertEquals(new ErrorCode("error.client.usernameExists"), firstError.errorCode());
        assertEquals(new ErrorSource("data.attributes.username"), firstError.errorSource());
        assertEquals(firstError.parameters().size(), 1);
        assertTrue(firstError.parameters().contains(new ErrorDetailParameter(username.value())));

        ErrorData secondError = errors.get(1);
        assertEquals(HttpStatus.BAD_REQUEST, secondError.httpStatus());
        assertEquals(new ErrorCode("error.client.emailExists"), secondError.errorCode());
        assertEquals(new ErrorSource("data.attributes.email"), secondError.errorSource());
        assertEquals(secondError.parameters().size(), 1);
        assertTrue(secondError.parameters().contains(new ErrorDetailParameter(email.value())));

        ErrorData lastError = errors.getLast();
        assertEquals(HttpStatus.BAD_REQUEST, lastError.httpStatus());
        assertEquals(new ErrorCode("error.client.phoneNumberExists"), lastError.errorCode());
        assertEquals(new ErrorSource("data.attributes.phoneNumber"), lastError.errorSource());
        assertEquals(lastError.parameters().size(), 1);
        assertTrue(lastError.parameters().contains(new ErrorDetailParameter(phone.value())));
    }

    private CreateNewUserCommand buildWriteCommand(Username user1, Email email1, PhoneNumber phoneNumber) {
        return new CreateNewUserCommand(
                user1,
                password(),
                email1,
                phoneNumber,
                firstName(),
                lastName(),
                city(),
                country()
        );
    }

    private ValidateNewUserDataCommand buildValidateCommand(Username user, Email email, PhoneNumber phoneNumber) {
        return new ValidateNewUserDataCommand(
                user,
                email,
                phoneNumber
        );
    }
}
