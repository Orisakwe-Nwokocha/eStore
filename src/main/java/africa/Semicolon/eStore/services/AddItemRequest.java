package africa.Semicolon.eStore.services;

import lombok.Data;

@Data
public final class AddItemRequest {
    private String productName;
    private int quantityOfProduct;
}
