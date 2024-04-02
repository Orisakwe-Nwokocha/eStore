package africa.Semicolon.eStore.utils;

import africa.Semicolon.eStore.data.models.Role;
import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.dtos.requests.RegisterRequest;
import africa.Semicolon.eStore.dtos.responses.LoginResponse;
import africa.Semicolon.eStore.dtos.responses.LogoutResponse;
import africa.Semicolon.eStore.dtos.responses.RegisterResponse;

import java.time.format.DateTimeFormatter;

import static africa.Semicolon.eStore.utils.Cleaner.lowerCaseValueOf;
import static africa.Semicolon.eStore.utils.Cleaner.upperCaseValueOf;
import static africa.Semicolon.eStore.utils.Cryptography.encode;

public final class Mapper {
    public static User map(RegisterRequest registerRequest) {
        String username = lowerCaseValueOf(registerRequest.getUsername());
        String password = encode(registerRequest.getPassword());
        String role = upperCaseValueOf(registerRequest.getRole());
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.valueOf(role));
        user.setName(registerRequest.getName());
        user.setEmailAddress(registerRequest.getEmailAddress());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        return user;
    }
    public static RegisterResponse mapRegisterResponseWith(User user) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(user.getId());
        registerResponse.setUsername(user.getUsername());
        return registerResponse;
    }

    public static LoginResponse mapLoginResponseWith(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setLoggedIn(true);
        return loginResponse;
    }

    public static LogoutResponse mapLogoutResponseWith(User user) {
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setId(user.getId());
        logoutResponse.setUsername(user.getUsername());
        logoutResponse.setLoggedIn(false);
        return logoutResponse;
    }


}
