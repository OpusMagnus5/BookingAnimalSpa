package pl.bodzioch.damian.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bodzioch.damian.command.CreateNewUserCommand;
import pl.bodzioch.damian.command.ValidateNewUserData;
import pl.bodzioch.damian.dto.CheckNewUserDataRequest;
import pl.bodzioch.damian.dto.CreateNewUserRequest;
import pl.bodzioch.damian.dto.CreateResourceResponse;
import pl.bodzioch.damian.dto.UserDto;

@RestController
@RequestMapping(UserController.ROOT_PATH)
@RequiredArgsConstructor
class UserController {

    public static final String ROOT_PATH = "/api/user";
    public static final String APPROVE_NEW = "/approve-new";

    private final IReadUserService readUserService;
    private final IWriteUserService writeUserService;

    //TODO dodać linki do resposne, poprawić requesty wq json API
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(APPROVE_NEW)
    void approveNewUserData(@Valid @RequestBody CheckNewUserDataRequest request) {
        readUserService.handle(new ValidateNewUserData(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    CreateResourceResponse createNewUser(@Valid @RequestBody CreateNewUserRequest request) {
        readUserService.handle(new ValidateNewUserData(request));
        //TODO walidacja sms
        UserDto userDto = writeUserService.handle(new CreateNewUserCommand(request));
        return new CreateResourceResponse(userDto);
    }
}
