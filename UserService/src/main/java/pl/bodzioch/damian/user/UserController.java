package pl.bodzioch.damian.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.command.FindUserByNatualIdCommand;
import pl.bodzioch.damian.command.ValidateNewUserDataCommand;
import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.dto.CreateResourceResponse;
import pl.bodzioch.damian.dto.ReadResourceResponse;
import pl.bodzioch.damian.dto.UserDto;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.ResourceLinkGenerator;
import pl.bodzioch.damian.valueobject.*;

import java.util.Collections;
import java.util.List;

import static pl.bodzioch.damian.utils.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping(ResourceLinkGenerator.ROOT_PATH)
@RequiredArgsConstructor
class UserController {

    private final IReadUserService readUserService;
    private final IWriteUserService writeUserService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    CreateResourceResponse createNewUser(@Valid @RequestBody CreateNewUserRequest request) {
        readUserService.handle(new ValidateNewUserDataCommand(request));
        //TODO walidacja sms
        UserDto userDto = writeUserService.handle(new CreateNewUserCommand(request));
        return new CreateResourceResponse(userDto);
    }

    @ResponseStatus(HttpStatus.OK) //TODO pododawać tłumaczenia błędów
    @GetMapping("/login")
    ReadResourceResponse login(@RequestParam(required = false) String username,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) String email) {
        validate(username, phoneNumber, email);
        FindUserByNatualIdCommand command = new FindUserByNatualIdCommand(new Username(username), new Email(email), new PhoneNumber(phoneNumber));
        UserDto user = readUserService.handle(command);
        return new ReadResourceResponse(user);
    }

    private void validate(String username, String phoneNumber, String email) {
        if (StringUtils.isAllBlank(username, phoneNumber, email)) {
            throw new UserAppException(BAD_REQUEST,
                    List.of(new ErrorData(
                            BAD_REQUEST,
                            new ErrorCode("error.client.loginNotProvided"),
                            new ErrorSource("RequestParam"),
                            Collections.emptyList()
                    )));
        }
    }
}
