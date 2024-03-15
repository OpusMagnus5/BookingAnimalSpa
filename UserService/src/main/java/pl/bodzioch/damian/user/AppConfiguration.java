package pl.bodzioch.damian.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class AppConfiguration {

    private final UserRepository userRepository;

    @Bean
    UserService userService() {
        return new UserService(userRepository);
    }
}
