package africa.Semicolon.eStore.dto.responses;

import lombok.Data;

@Data
public final class LogoutResponse {
    private String id;
    private String username;
    private boolean isLoggedIn;
}