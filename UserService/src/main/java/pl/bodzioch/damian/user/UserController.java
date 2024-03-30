package pl.bodzioch.damian.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.command.ValidateNewUserDataCommand;
import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.dto.CreateResourceResponse;
import pl.bodzioch.damian.dto.UserDto;
import pl.bodzioch.damian.utils.ResourceLinkGenerator;

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
}
