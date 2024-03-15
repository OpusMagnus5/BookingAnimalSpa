package pl.bodzioch.damian.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<UserEntity, Long>, UserRepository {
}
