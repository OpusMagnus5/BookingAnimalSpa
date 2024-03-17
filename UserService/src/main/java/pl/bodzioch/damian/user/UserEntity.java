package pl.bodzioch.damian.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.generator.EventType;
import pl.bodzioch.damian.utils.GeneratedUuidValue;
import pl.bodzioch.damian.utils.UserEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "users")
@OptimisticLocking
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_gen")
    @SequenceGenerator(name = "client_id_gen", sequenceName = "client_id_seq")
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @GeneratedUuidValue(types = EventType.INSERT)
    private UUID uuid;

    @Version
    private Integer version;

    @NaturalId
    private String username;

    private String password;

    @NaturalId
    private String email;

    private String city;

    private String country;

    @Column(name = "telephone_number")
    private String phoneNumber;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    static UserEntity of(User user) {
        return UserEntity.builder()
                .username(user.username().value())
                .password(UserEncoder.encodePassword(user.password()))
                .email(user.email().value())
                .city(user.city().value())
                .country(user.country().value().getCountry())
                .phoneNumber(user.phoneNumber().value())
                .build();
    }
}
