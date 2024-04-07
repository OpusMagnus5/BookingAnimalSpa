package pl.bodzioch.damian.security;

import org.springframework.security.core.Authentication;

public interface ITokenService {
    String generateToken(Authentication authentication);
}
