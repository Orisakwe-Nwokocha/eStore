package africa.Semicolon.eStore.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class FindProductRequest {
    @NotNull(message = "Product id cannot be null")
    private String productId;
}
