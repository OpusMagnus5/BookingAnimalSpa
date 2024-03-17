package pl.bodzioch.damian.user;

import java.util.Iterator;

class TestUserWriteRepository extends TestUserRepository implements IUserWriteRepository {

    @Override
    public User createNew(UserEntity user) {
        Long id = getNextId();
        user.setId(id);
        repository.put(id, user);
        UserEntity userEntity = repository.get(id);
        return new User(userEntity);
    }

    private Long getNextId() {
        Iterator<Long> iterator = repository.keys().asIterator();
        Long id = 1L;
        while (iterator.hasNext()) {
            id = iterator.next();
        }
        return id;
    }
}
