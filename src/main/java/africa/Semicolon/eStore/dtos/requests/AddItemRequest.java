package africa.Semicolon.eStore.dtos.requests;

import lombok.Data;

@Data
public final class AddItemRequest {
    private String username;
    private String productId;
    private int quantityOfProduct;
}
