package africa.Semicolon.eStore.dto.responses;

import africa.Semicolon.eStore.data.models.ShoppingCart;
import lombok.Data;

@Data
public final class ViewCartResponse {
    private String username;
    private ShoppingCart shoppingCart;
}
