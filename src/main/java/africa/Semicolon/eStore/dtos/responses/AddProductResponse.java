package africa.Semicolon.eStore.dtos.responses;

import lombok.Data;

@Data
public final class AddProductResponse {
    private String productId;
    private String productName;
    private String category;
    private String description;
    private Double price;
    private Integer quantity;
}
