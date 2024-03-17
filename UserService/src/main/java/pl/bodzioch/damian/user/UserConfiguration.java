package pl.bodzioch.damian.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class UserConfiguration {

    private final IUserRepository userRepository;

    @Bean
    IUserService userService() {
        return new UserService(userRepository);
    }
}
