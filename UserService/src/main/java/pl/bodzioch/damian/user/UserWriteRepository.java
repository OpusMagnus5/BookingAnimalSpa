package pl.bodzioch.damian.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
class UserWriteRepository implements IUserWriteRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public User createNew(UserEntity user) {
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();
        return new User(user);
    }
}
