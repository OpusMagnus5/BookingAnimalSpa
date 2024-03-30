package pl.bodzioch.damian.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
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
import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.dto.UserDto;
import pl.bodzioch.damian.dto.WriteAttributesDto;
import pl.bodzioch.damian.dto.WriteDataDto;
import pl.bodzioch.damian.util.JsonToString;
import pl.bodzioch.damian.utils.ResourceLinkGenerator;

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
                .baseUrl(ResourceLinkGenerator.ROOT_PATH)
                .build();
    }

    @AfterAll
    static void cleanUp() {
        mdcMocked.close();
    }

    @Test
    void Create_new_user_data_When_user_with_unique_data_exists() throws IOException {
        String user = username().value();
        String mail = email().value();
        String phone = phoneNumber().value();

        CreateNewUserRequest createRequest = buildCreateNewUserRequest(user, mail, phone);
        client.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(createRequest)
                .exchange()
                .expectStatus().isCreated();

        CreateNewUserRequest checkRequest = buildCreateNewUserRequest(user, mail, phone);
        client.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json(JsonToString.getResponseJson("Create_new_user_data_When_user_with_unique_data_exists"));
    }

    @Test
    void Create_new_user_data_When_request_is_incorrect() throws IOException {
        CreateNewUserRequest incorrectRequest = buildIncorrectCreateNewUserRequest();
        client.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(incorrectRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json(JsonToString.getResponseJson("Create_new_user_data_When_request_is_incorrect"));
    }

    @Test
    void Create_new_user_data_When_request_is_correct() {
        CreateNewUserRequest request = buildCreateNewUserRequest(username().value(), email().value(), phoneNumber().value());
        client.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$..type").isEqualTo(UserDto.TYPE)
                .jsonPath("$..attributes.id").isNotEmpty()
                .jsonPath("$..attributes.username").isEqualTo(request.data().attributes().username())
                .jsonPath("$..attributes.email").isEqualTo(request.data().attributes().email())
                .jsonPath("$..attributes.firstName").isEqualTo(request.data().attributes().firstName())
                .jsonPath("$..attributes.lastName").isEqualTo(request.data().attributes().lastName())
                .jsonPath("$..attributes.city").isEqualTo(request.data().attributes().city())
                .jsonPath("$..attributes.country").isEqualTo(request.data().attributes().country())
                .jsonPath("$..attributes.phoneNumber").isEqualTo(request.data().attributes().phoneNumber())
                .jsonPath("$..attributes.active").isEqualTo(false)
                .jsonPath("$..attributes.createTime").isNotEmpty()
                .jsonPath("$..attributes.modifyTime").isNotEmpty()
                .jsonPath("$.links.self").isNotEmpty();
    }

    private CreateNewUserRequest buildCreateNewUserRequest(String username, String email, String phone) {
        return new CreateNewUserRequest(
                new WriteDataDto(
                        UserDto.TYPE,
                        buildWriteAttributesDto(username, email, phone)
                ),
                null
        );
    }

    private WriteAttributesDto buildWriteAttributesDto(String username, String email, String phone) {
        return new WriteAttributesDto(
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

    private CreateNewUserRequest buildIncorrectCreateNewUserRequest() {
        return new CreateNewUserRequest(
                new WriteDataDto(
                        getIncorrectValue(),
                        new WriteAttributesDto(
                                getIncorrectValue(),
                                getIncorrectValue(),
                                getIncorrectValue(),
                                getIncorrectValue(),
                                getIncorrectValue(),
                                getIncorrectValue(),
                                getIncorrectValue(),
                                getIncorrectValue()
                        )
                ),
                null
        );
    }
}
