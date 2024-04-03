package africa.Semicolon.eStore.dtos.responses;

import lombok.Data;

@Data
public final class AddItemResponse {
    private String itemId;
    private String product;
    private int quantityOfProduct;
}
