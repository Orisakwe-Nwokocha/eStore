package africa.Semicolon.eStore.utils;

import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.dtos.requests.LoginRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Cryptography {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean isMatches(LoginRequest loginRequest, User user) {
        String rawPassword = loginRequest.getPassword();
        String encodedPassword = user.getPassword();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
