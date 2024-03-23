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
import pl.bodzioch.damian.dto.UserAppErrorResponse;

import java.util.UUID;

import static pl.bodzioch.damian.exception.UserAppException.REQUEST_ID_MDC_PARAM;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    void Approve_new_user_data_When_user_with_unique_data_exists(){
        String user = "user";
        String mail = "email@email.com";
        String phone = "111111111";

        CreateNewUserRequest createRequest = buildCreateNewUserRequest(user, mail, phone);
        client.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(createRequest)
                .exchange()
                .expectStatus().isCreated();

        CheckNewUserDataRequest checkRequest = buildCheckNewUserRequest(user, mail, phone);
        UserAppErrorResponse responseBody = client.post()
                .uri(UserController.APPROVE_NEW)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserAppErrorResponse.class).returnResult().getResponseBody();
    }

    private CheckNewUserDataRequest buildCheckNewUserRequest(String username, String email, String phone) {
        return new CheckNewUserDataRequest(
                username,
                "password1",
                email,
                phone,
                "Name",
                "Lastname",
                "City",
                "PL"
        );
    }
    private CreateNewUserRequest buildCreateNewUserRequest(String username, String email, String phone) {
        return new CreateNewUserRequest(
                username,
                "password1",
                email,
                phone,
                "Name",
                "Lastname",
                "City",
                "PL",
                "111111"
        );
    }
}
