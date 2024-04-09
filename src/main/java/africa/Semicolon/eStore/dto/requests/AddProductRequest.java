package africa.Semicolon.eStore.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class AddProductRequest {
    private String description;
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Product name cannot be null")
    private String name;
    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be greater than or equal to 1")
    private double price;
    @NotNull(message = "Category cannot be null")
    private String category;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private int quantity;
}
