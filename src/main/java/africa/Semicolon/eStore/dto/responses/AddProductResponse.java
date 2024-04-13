package africa.Semicolon.eStore.dto.responses;

import lombok.Data;

@Data
public final class AddProductResponse {
    private String productId;
    private String productName;
    private String category;
    private String description;
    private String price;
    private Integer quantity;
}
