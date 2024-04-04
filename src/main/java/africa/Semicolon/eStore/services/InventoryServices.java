package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Product;
import africa.Semicolon.eStore.dtos.requests.AddProductRequest;
import africa.Semicolon.eStore.dtos.requests.FindProductRequest;
import africa.Semicolon.eStore.dtos.responses.AddProductResponse;
import africa.Semicolon.eStore.dtos.responses.FindProductResponse;
import africa.Semicolon.eStore.dtos.responses.FindAllProductsResponse;

public interface InventoryServices {
    Product findBy(String id);
    AddProductResponse addProductWith(AddProductRequest addProductRequest);
    FindAllProductsResponse findAllProducts();
    FindProductResponse findProductWith(FindProductRequest findProductRequest);
}
