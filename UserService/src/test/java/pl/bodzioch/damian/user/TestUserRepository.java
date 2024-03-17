package pl.bodzioch.damian.user;

import java.util.concurrent.ConcurrentHashMap;

class TestUserRepository {
    protected static final ConcurrentHashMap<Long, UserEntity> repository = new ConcurrentHashMap<>();
}
