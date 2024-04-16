package africa.Semicolon.eStore.dto.responses;

import lombok.Data;

@Data
public class GetItemResponse {
    private String productId;
    private String productName;
    private String category;
    private String description;
    private String price;
    private int quantityOfProduct;
}
