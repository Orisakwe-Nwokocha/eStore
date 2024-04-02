package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.dtos.requests.RegisterRequest;
import africa.Semicolon.eStore.dtos.responses.RegisterResponse;

public interface UserServices {
    RegisterResponse register(RegisterRequest registerRequest);
}
