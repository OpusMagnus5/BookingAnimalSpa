package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class ReadUserService implements IReadUserService {

    private final IUserReadRepository userReadRepository;
}
