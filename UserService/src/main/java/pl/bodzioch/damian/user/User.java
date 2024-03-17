package pl.bodzioch.damian.user;

import pl.bodzioch.damian.valueobject.*;

import java.util.Locale;

record User(
        UserId id,
        Username username,
        Password password,
        Email email,
        PhoneNumber phoneNumber,
        City city,
        Country country,
        CompanyId companyId,
        CreateTime createTime,
        ModifyTime modifyTime
) {
    public User(Username username, Password password, Email email, PhoneNumber phoneNumber, City city, Country country) {
        this(null, username, password, email, phoneNumber, city, country, null, null, null);
    }

    public User(UserEntity userEntity) {
        this(
                new UserId(userEntity.getId()),
                new Username(userEntity.getUsername()),
                new Password(userEntity.getPassword()),
                new Email(userEntity.getEmail()),
                new PhoneNumber(userEntity.getPhoneNumber()),
                new City(userEntity.getCity()),
                new Country(Locale.of(userEntity.getCountry())),
                null,
                new CreateTime(userEntity.getCreateTime()),
                new ModifyTime(userEntity.getModifyTime())
        );
    }
}
