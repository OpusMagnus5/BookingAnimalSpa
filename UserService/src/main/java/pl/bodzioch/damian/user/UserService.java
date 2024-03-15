package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class UserService {

    private final UserRepository userRepository;

    void createNewUser(User user) {

    }
}
