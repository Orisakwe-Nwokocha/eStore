package africa.Semicolon.eStore.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class AddItemRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Product id cannot be null")
    private String productId;
    @NotNull(message = "Quantity cannot be null")
    private int quantityOfProduct;
}
