package pl.bodzioch.damian.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bodzioch.damian.dto.CheckNewUserDataRequest;

@RestController
@RequestMapping("/api")
public class UserController {

    CreateNewUserResponse approveNewUserData(@Valid @RequestBody CheckNewUserDataRequest request) {
        //TODO
    }
}
