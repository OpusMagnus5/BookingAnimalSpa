package pl.bodzioch.damian.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;
import pl.bodzioch.damian.command.CreateNewUserCommand;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq")
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String city;

    private String country;

    @NaturalId
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "active")
    private Boolean isActive;

//    @Column(name = "company_id")
//    private Long companyId;

    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "modify_time")
    @UpdateTimestamp
    private LocalDateTime modifyTime;

    protected UserEntity(CreateNewUserCommand command) {
        username = command.username().value();
        password= UserEncoder.encodePassword(command.password());
        email = command.email().value();
        phoneNumber = command.phoneNumber().value();
        firstName = command.firstName().value();
        lastName = command.lastName().value();
        city = command.city().value();
        country = command.country().value().getLanguage().toUpperCase();
        isActive = false;
    }

}
