package pl.bodzioch.damian.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.generator.EventType;
import pl.bodzioch.damian.utils.GeneratedUuidValue;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "users")
@OptimisticLocking
@Getter
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_gen")
    @SequenceGenerator(name = "client_id_gen", sequenceName = "client_id_seq")
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
}
