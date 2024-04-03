package africa.Semicolon.eStore.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class RemoveItemRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Product id cannot be null")
    private String productId;
}
