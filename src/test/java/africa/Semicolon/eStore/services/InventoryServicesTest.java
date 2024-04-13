package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dto.requests.*;
import africa.Semicolon.eStore.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class InventoryServicesTest {
    @Autowired
    private InventoryServices inventoryServices;
    @Autowired
    private Inventory inventory;
    @Autowired
    private UserServices userServices;
    @Autowired
    private Users users;

    private RegisterRequest registerRequest;
    private AddProductRequest addProductRequest;
    private FindProductRequest findProductRequest;

    @BeforeEach
    public void setUp() {
        users.deleteAll();
        inventory.deleteAll();

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setRole("admin");

        addProductRequest = new AddProductRequest();
        addProductRequest.setUsername("username");
        addProductRequest.setProductName("pixel 6");
        addProductRequest.setDescription("smartphone");
        addProductRequest.setQuantity(10);
        addProductRequest.setCategory("electronics");
        addProductRequest.setPrice(350_000);

        findProductRequest = new FindProductRequest();
    }

    @Test
    public void findAllProductsTest() {
        userServices.register(registerRequest);
        userServices.addProduct(addProductRequest);

        var findAllProductsResponse = inventoryServices.findAllProducts();
        assertThat(findAllProductsResponse.getProducts(), notNullValue());
    }

    @Test
    public void givenExistingProduct_findProductTest() {
        userServices.register(registerRequest);
        var addProductResponse = userServices.addProduct(addProductRequest);
        findProductRequest.setProductId(addProductResponse.getProductId());
        var findProductResponse = inventoryServices.findProductWith(findProductRequest);
        assertThat(findProductResponse.getProduct(), notNullValue());
    }

    @Test
    public void givenNonExistingProduct_findProduct_throwsProductNotFoundExceptionTest() {
        userServices.register(registerRequest);
        userServices.addProduct(addProductRequest);
        findProductRequest.setProductId("non-existing-product-id");
        try {
            inventoryServices.findProductWith(findProductRequest);
        }
        catch (ProductNotFoundException e) {
            assertThat(e.getMessage(), containsString("Product not found"));
        }
    }

}