package africa.Semicolon.eStore.dtos.responses;

import lombok.Data;

@Data
public final class AddProductResponse {
    private String productId;
    private String productName;
    private String category;
    private String description;
    private double price;
    private int quantity;
}
