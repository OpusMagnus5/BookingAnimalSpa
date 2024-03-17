package pl.bodzioch.damian.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class UserConfiguration {

    private final IUserWriteRepository userWriteRepository;
    private final IUserReadRepository userReadRepository;

    @Bean
    IWriteUserService writeUserService() {
        return new WriteUserService(userWriteRepository, userReadRepository);
    }

    @Bean
    IReadUserService readUserService() {
        return new ReadUserService(userReadRepository);
    }
}
