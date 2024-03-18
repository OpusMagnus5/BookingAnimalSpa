package pl.bodzioch.damian.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class WriteUserService implements IWriteUserService {

    private final IUserWriteRepository userWriteRepository;
    private final IUserReadRepository userReadRepository;


}
