package pl.bodzioch.damian.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import pl.bodzioch.damian.dto.CheckNewUserDataRequest;
import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.util.JsonToString;

import java.io.IOException;
import java.util.UUID;

import static pl.bodzioch.damian.exception.UserAppException.REQUEST_ID_MDC_PARAM;
import static pl.bodzioch.damian.util.TestDataGenerator.*;

@SpringBootTest
@Transactional
@Rollback
class UserIntegrationTest {

    private WebTestClient client;

    @Autowired
    private WebApplicationContext applicationContext;

    private static MockedStatic<MDC> mdcMocked;

    @BeforeAll
    static void init() {
        mdcMocked = Mockito.mockStatic(MDC.class);
        mdcMocked.when(() -> MDC.get(REQUEST_ID_MDC_PARAM)).thenReturn(UUID.randomUUID().toString());
    }

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl(UserController.ROOT_PATH)
                .build();
    }

    @Test
    void Approve_new_user_data_When_user_with_unique_data_exists() throws IOException {
        String user = username().value();
        String mail = email().value();
        String phone = phoneNumber().value();

        CreateNewUserRequest createRequest = buildCreateNewUserRequest(user, mail, phone);
        client.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(createRequest)
                .exchange()
                .expectStatus().isCreated();

        CheckNewUserDataRequest checkRequest = buildCheckNewUserRequest(user, mail, phone);
        client.post()
                .uri(UserController.APPROVE_NEW)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json(JsonToString.getResponseJson("Approve_new_user_data_When_user_with_unique_data_exists"));
    }

    @Test
    void Approve_new_user_data_When_user_data_are_incorrect() throws IOException {
        CheckNewUserDataRequest request = getCheckNewUserRequestWithAllIncorrectData();
        client.post()
                .uri(UserController.APPROVE_NEW)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json(JsonToString.getResponseJson("Approve_new_user_data_When_user_data_are_incorrect"));
    }

    private CreateNewUserRequest buildCreateNewUserRequest(String username, String email, String phone) {
        return new CreateNewUserRequest(
                username,
                password().value(),
                email,
                phone,
                firstName().value(),
                lastName().value(),
                city().value(),
                country().value().getLanguage().toUpperCase(),
                smsCode().value()
        );
    }

    private CheckNewUserDataRequest buildCheckNewUserRequest(String username, String email, String phone) {
        return new CheckNewUserDataRequest(
                username,
                password().value(),
                email,
                phone,
                firstName().value(),
                lastName().value(),
                city().value(),
                country().value().getLanguage().toUpperCase()
        );
    }
    private CheckNewUserDataRequest getCheckNewUserRequestWithAllIncorrectData() {
        return new CheckNewUserDataRequest(
                getIncorrectValue(),
                getIncorrectValue(),
                getIncorrectValue(),
                getIncorrectValue(),
                getIncorrectValue(),
                getIncorrectValue(),
                getIncorrectValue(),
                getIncorrectValue()
        );
    }
}
