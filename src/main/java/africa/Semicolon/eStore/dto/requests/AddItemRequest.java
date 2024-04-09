package africa.Semicolon.eStore.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class AddItemRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Product id cannot be null")
    private String productId;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private int quantityOfProduct;
}
