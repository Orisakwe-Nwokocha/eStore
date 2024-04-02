package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.dtos.requests.AddProductRequest;
import africa.Semicolon.eStore.dtos.responses.AddProductResponse;

public interface InventoryServices {
    AddProductResponse addProductWith(AddProductRequest addProductRequest);
}
