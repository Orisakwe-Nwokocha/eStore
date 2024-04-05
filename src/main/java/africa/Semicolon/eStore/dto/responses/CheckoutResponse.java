package africa.Semicolon.eStore.dto.responses;

import lombok.Data;

@Data
public final class CheckoutResponse {
    private String username;
    private String orderId;
}
