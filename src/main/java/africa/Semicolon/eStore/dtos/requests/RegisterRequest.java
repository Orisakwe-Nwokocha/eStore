package africa.Semicolon.eStore.dtos.requests;

import africa.Semicolon.eStore.data.models.Address;
import africa.Semicolon.eStore.data.models.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    @NotNull(message = "Please enter your address")
    private Address homeAddress;
    @NotNull(message = "Please enter a username")
    private String username;
    @NotNull(message = "Please enter a password")
    private String password;
    @NotNull(message = "Please select a role")
    private Role role;
}
