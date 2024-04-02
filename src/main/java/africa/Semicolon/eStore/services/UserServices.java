package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.dtos.requests.LoginRequest;
import africa.Semicolon.eStore.dtos.requests.LogoutRequest;
import africa.Semicolon.eStore.dtos.requests.RegisterRequest;
import africa.Semicolon.eStore.dtos.responses.LoginResponse;
import africa.Semicolon.eStore.dtos.responses.LogoutResponse;
import africa.Semicolon.eStore.dtos.responses.RegisterResponse;

public interface UserServices {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    LogoutResponse logout(LogoutRequest logOutRequest);
}
