package africa.Semicolon.eStore.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class ViewOrderRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "OrderId cannot be null")
    private String orderId;
}
