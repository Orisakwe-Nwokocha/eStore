package africa.Semicolon.eStore.dtos.responses;

import lombok.Data;

@Data
public final class RegisterResponse {
    private String id;
    private String username;
    private String dateTimeRegistered;
}