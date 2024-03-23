package pl.bodzioch.damian.user;

import pl.bodzioch.damian.command.ValidateNewUserData;
import pl.bodzioch.damian.exception.UserAppException;
import pl.bodzioch.damian.utils.HttpStatus;
import pl.bodzioch.damian.valueobject.*;

import java.util.ArrayList;
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
        List<ErrorData> errorData = new ArrayList<>();
        if (command.username().equals(username)) {
            errorData.add(buildUserExistsByUsernameException(command.username()));
        }
        if (command.email().equals(email)) {
            errorData.add(throwUserExistsByEmailException(command.email()));
        }
        if (command.phoneNumber().equals(phoneNumber)) {
            errorData.add(throwUserExistsByPhoneException(command.phoneNumber()));
        }
        throw new UserAppException(HttpStatus.BAD_REQUEST, errorData);
    }

    private ErrorData buildUserExistsByUsernameException(Username username) {
        return new ErrorData(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.usernameExists"),
                new ErrorSource("data/attributes/username"),
                List.of(new ErrorDetailParameter(username.value()))
        );
    }

    private ErrorData throwUserExistsByEmailException(Email email) {
        return new ErrorData(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.emailExists"),
                new ErrorSource("data/attributes/email"),
                List.of(new ErrorDetailParameter(email.value()))
        );
    }

    private ErrorData throwUserExistsByPhoneException(PhoneNumber phoneNumber) {
        return new ErrorData(
                HttpStatus.BAD_REQUEST,
                new ErrorCode("error.client.phoneNumberExists"),
                new ErrorSource("data/attributes/phoneNumber"),
                List.of(new ErrorDetailParameter(phoneNumber.value()))
        );
    }
}
