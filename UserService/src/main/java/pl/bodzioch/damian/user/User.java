package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.ValidateNewUserData;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.List;
import java.util.Locale;

record User(
        UserId id,
        Username username,
        Password password,
        Email email,
        PhoneNumber phoneNumber,
        FirstName firstName,
        LastName lastName,
        City city,
        Country country,
        IsActive isActive,
        CompanyId companyId,
        CreateTime createTime,
        ModifyTime modifyTime
) {
    public User(UserEntity userEntity) {
        this(
                new UserId(userEntity.getId()),
                new Username(userEntity.getUsername()),
                new Password(userEntity.getPassword()),
                new Email(userEntity.getEmail()),
                new PhoneNumber(userEntity.getPhoneNumber()),
                new FirstName(userEntity.getFirstName()),
                new LastName(userEntity.getLastName()),
                new City(userEntity.getCity()),
                new Country(Locale.of(userEntity.getCountry())),
                new IsActive(userEntity.getIsActive()),
                null,
                new CreateTime(userEntity.getCreateTime()),
                new ModifyTime(userEntity.getModifyTime())
        );
    }

    void checkIncorrectParameter(ValidateNewUserData command) {
        if (command.username().equals(username)) {
            throwUserExistsByUsernameException(command.username());
        } else if (command.email().equals(email)) {
            throwUserExistsByEmailException(command.email());
        } else if (command.phoneNumber().equals(phoneNumber)) {
            throwUserExistsByPhoneException(command.phoneNumber());
        }
    }

    private void throwUserExistsByUsernameException(Username username) {
        throw new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.usernameExists"),
                new ErrorSource("data/attributes/username"),
                List.of(new ErrorDetailParameter(username.value()))
        );
    }

    private void throwUserExistsByEmailException(Email email) {
        throw new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.emailExists"),
                new ErrorSource("data/attributes/email"),
                List.of(new ErrorDetailParameter(email.value()))
        );
    }

    private void throwUserExistsByPhoneException(PhoneNumber phoneNumber) {
        throw new UserAppException(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.phoneNumberExists"),
                new ErrorSource("data/attributes/phoneNumber"),
                List.of(new ErrorDetailParameter(phoneNumber.value()))
        );
    }
}
