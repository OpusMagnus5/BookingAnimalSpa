package pl.bodzioch.damian.utils;

import pl.bodzioch.damian.valueobject.UserId;

public class ResourceLinkGenerator {

    public static final String ROOT_PATH = "/api/user/";
    private static final String BASE_URL = "http://user-service";

    public static String userResource(UserId userId) {
        return BASE_URL + ROOT_PATH + userId.value().toString();
    }
}
