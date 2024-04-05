package africa.Semicolon.eStore.data.models;

import lombok.Data;

@Data
public final class Item {
    private Product product;
    private Integer quantityOfProduct;

    @Override
    public String toString() {
        return String.format("%nProduct:%n%s%nQuantity: %d", product, quantityOfProduct);
    }
}
