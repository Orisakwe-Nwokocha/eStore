package africa.Semicolon.eStore.dto.responses;

import africa.Semicolon.eStore.data.models.ShoppingCart;
import lombok.Data;

@Data
public final class AddItemResponse {
    private String username;
    private ShoppingCart shoppingCart;
}
