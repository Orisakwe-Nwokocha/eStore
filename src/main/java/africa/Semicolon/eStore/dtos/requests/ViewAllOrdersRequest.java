package africa.Semicolon.eStore.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class ViewAllOrdersRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
}
