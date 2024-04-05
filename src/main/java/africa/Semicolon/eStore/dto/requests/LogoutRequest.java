package africa.Semicolon.eStore.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class LogoutRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
}