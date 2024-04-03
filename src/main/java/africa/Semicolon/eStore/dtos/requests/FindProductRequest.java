package africa.Semicolon.eStore.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class FindProductRequest {
    @NotNull(message = "Product name cannot be null")
    private String productId;
}
