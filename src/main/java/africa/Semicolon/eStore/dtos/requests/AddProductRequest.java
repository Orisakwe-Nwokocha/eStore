package africa.Semicolon.eStore.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class AddProductRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Product name cannot be null")
    private String name;
    @NotNull(message = "Price cannot be null")
    private double price;
    private String description;
    @NotNull(message = "Category cannot be null")
    private String category;
    @NotNull(message = "Quantity cannot be null")
    private int quantity;
}
