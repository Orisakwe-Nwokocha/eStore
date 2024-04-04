package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Item;
import africa.Semicolon.eStore.data.models.Product;
import africa.Semicolon.eStore.data.models.ShoppingCart;
import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.dtos.requests.AddProductRequest;
import africa.Semicolon.eStore.dtos.requests.FindProductRequest;
import africa.Semicolon.eStore.dtos.responses.AddProductResponse;
import africa.Semicolon.eStore.dtos.responses.FindAllProductsResponse;
import africa.Semicolon.eStore.dtos.responses.FindProductResponse;
import africa.Semicolon.eStore.exceptions.IllegalProductStateException;
import africa.Semicolon.eStore.exceptions.InvalidArgumentException;
import africa.Semicolon.eStore.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.Semicolon.eStore.utils.Mapper.*;

@Service
public class InventoryServicesImpl implements InventoryServices {
    @Autowired
    private Inventory inventory;

    @Override
    public Product findBy(String id) {
        return inventory.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public AddProductResponse addProductWith(AddProductRequest addProductRequest) {
        Product newProduct = mapAddItemResponse(addProductRequest);
        inventory.save(newProduct);
        return mapAddProductResponseWith(newProduct);
    }

    @Override
    public FindAllProductsResponse findAllProducts() {
        return mapGetProductsResponse(inventory.findAll());
    }

    @Override
    public FindProductResponse findProductWith(FindProductRequest findProductRequest) {
        Product foundProduct = findBy(findProductRequest.getProductId());
        return mapFindProductResponse(foundProduct);
    }

    @Override
    public void validate(int quantityOfProduct, Product product) {
        if (quantityOfProduct <= 0) throw new InvalidArgumentException("Quantity of product must be positive");
        if (product.getQuantity() <= 0) throw new IllegalProductStateException("Product is out of stock.");
        if (quantityOfProduct > product.getQuantity()) throw new InvalidArgumentException("Quantity of product selected exceeds available stock");
    }

    @Override
    public void validate(ShoppingCart shoppingCart) {
        shoppingCart.getItems().forEach(item -> validate(item.getQuantityOfProduct(), item.getProduct()));
//        for (Item item : shoppingCart.getItems()) validate(item.getQuantityOfProduct(), item.getProduct());
    }

    @Override
    public void updateProductQuantity(List<Item> items) {

    }
}
