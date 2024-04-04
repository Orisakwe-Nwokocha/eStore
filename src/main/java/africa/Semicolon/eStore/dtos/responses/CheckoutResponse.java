package africa.Semicolon.eStore.dtos.responses;

import lombok.Data;

@Data
public final class CheckoutResponse {
    private String username;
    private String order;
}
