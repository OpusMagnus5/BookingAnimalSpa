package pl.bodzioch.damian.user;

import pl.bodzioch.damian.valueobject.*;

record User(
        UserId id,
        Username username,
        Password password,
        Email email, City city,
        PhoneNumber phoneNumber,
        CompanyId companyId
) {
}
