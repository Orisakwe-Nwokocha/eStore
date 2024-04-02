package africa.Semicolon.eStore.dtos.requests;

import lombok.Data;

@Data
public final class LoginRequest {
    private String username;
    private String password;
}