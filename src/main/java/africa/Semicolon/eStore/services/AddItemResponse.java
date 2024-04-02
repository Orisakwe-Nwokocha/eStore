package africa.Semicolon.eStore.services;

import lombok.Data;

@Data
public final class AddItemResponse {
    private String itemId;
    private String product;
    private int quantityOfProduct;
}
