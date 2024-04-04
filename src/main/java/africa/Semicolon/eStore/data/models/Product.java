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

    @Override
    public String toString() {
        String asterisk = "*".repeat(20);
        String format = "%s%n%s%n%s%n%s%n₦%,.2f%n%s";
        return String.format(format, asterisk, name, description, category, price, asterisk);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Product product) && this.id.equals(product.id);
    }
}
