package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Item;
import africa.Semicolon.eStore.data.models.Product;
import africa.Semicolon.eStore.dto.requests.AddProductRequest;
import africa.Semicolon.eStore.dto.requests.FindProductRequest;
import africa.Semicolon.eStore.dto.requests.RemoveProductRequest;
import africa.Semicolon.eStore.dto.responses.AddProductResponse;
import africa.Semicolon.eStore.dto.responses.FindProductResponse;
import africa.Semicolon.eStore.dto.responses.FindAllProductsResponse;
import africa.Semicolon.eStore.dto.responses.RemoveProductResponse;

import java.util.List;

public interface InventoryServices {
    Product findBy(String id);
    AddProductResponse addProductWith(AddProductRequest addProductRequest);
    RemoveProductResponse removeProductWith(RemoveProductRequest removeProductRequest);
    FindProductResponse findProductWith(FindProductRequest findProductRequest);
    FindAllProductsResponse findAllProducts();
    void validate(int quantityOfProduct, Product product);
    void validate(List<Item> items);
    void updateProductQuantity(List<Item> items);
}
