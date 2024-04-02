package africa.Semicolon.eStore.data.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Inventory")
public final class Product {
    @Id
    private String id;
    @NotNull(message = "Product name cannot be null")
    private String name;
    @NotNull(message = "Price cannot be null")
    private double price;
    private String description;
    @NotNull(message = "Category cannot be null")
    private ProductCategory category;
    @NotNull(message = "Quantity cannot be null")
    private int quantity;
}
