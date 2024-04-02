package africa.Semicolon.eStore.dtos.responses;

import lombok.Data;

@Data
public class LogoutResponse {
    private String id;
    private String username;
    private boolean isLoggedIn;
}