package africa.Semicolon.eStore.services;

import lombok.Data;

@Data
public final class RemoveItemRequest {
    private String username;
    private String productId;
}
