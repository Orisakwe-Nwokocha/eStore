package africa.Semicolon.eStore.data.models;

import lombok.Data;

@Data
public final class Item {
    private Product product;
    private int quantityOfProduct;
}
