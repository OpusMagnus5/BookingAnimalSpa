package pl.bodzioch.damian.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.bodzioch.damian.valueobject.Email;
import pl.bodzioch.damian.valueobject.PhoneNumber;
import pl.bodzioch.damian.valueobject.UserId;
import pl.bodzioch.damian.valueobject.Username;

import java.util.Optional;

@Slf4j
@Repository
class UserReadRepository implements IUserReadRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getByNaturalIds(Username username, Email email, PhoneNumber phoneNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        query.select(root);
        Predicate usernamePredicate = builder.equal(root.get("username"), username.value());
        Predicate emailPredicate = builder.equal(root.get("email"), email.value());
        Predicate phonePredicate = builder.equal(root.get("phoneNumber"), phoneNumber.value());
        Predicate usernameOrEmail = builder.or(usernamePredicate, emailPredicate, phonePredicate);
        query.where(usernameOrEmail);
        try {
            UserEntity result = entityManager.createQuery(query).getSingleResult();
            return Optional.of(new User(result));
        } catch (NoResultException e) {
            log.info("User with the username {} or email {} or phone {} was not found", username.value(), email.value(), phoneNumber.value());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(UserId userId) {
        UserEntity userEntity = entityManager.find(UserEntity.class, userId.value());
        return Optional.ofNullable(userEntity)
                .map(User::new);
    }
}
