package africa.Semicolon.eStore.dtos.responses;

import africa.Semicolon.eStore.data.models.Product;
import lombok.Data;

import java.util.List;

@Data
public final class GetProductsResponse {
    private String products;
}
