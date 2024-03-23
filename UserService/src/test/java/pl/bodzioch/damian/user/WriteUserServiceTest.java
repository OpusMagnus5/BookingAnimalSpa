package pl.bodzioch.damian.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.MDC;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.dto.UserDto;
import pl.bodzioch.damian.utils.UserEncoder;
import pl.bodzioch.damian.valueobject.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static pl.bodzioch.damian.exception.UserAppException.REQUEST_ID_MDC_PARAM;
import static pl.bodzioch.damian.util.TestDataGenerator.*;

class WriteUserServiceTest {

    private final UserConfiguration userConfiguration = new UserConfiguration(
            new TestUserWriteRepository(), new TestUserReadRepository()
    );
    private final IWriteUserService writeUserService = userConfiguration.writeUserService();
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
    void Create_new_user_When_data_are_valid() {
        CreateNewUserCommand user1Command = buildWriteCommand(username(), email(), phoneNumber());
        CreateNewUserCommand user2Command = buildWriteCommand(username(), email(), phoneNumber());
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
        assertFalse(userDto.active().value());
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
}
