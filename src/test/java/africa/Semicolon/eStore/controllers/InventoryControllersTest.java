package africa.Semicolon.eStore.controllers;

import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dto.requests.AddProductRequest;
import africa.Semicolon.eStore.dto.requests.FindProductRequest;
import africa.Semicolon.eStore.dto.requests.RegisterRequest;
import africa.Semicolon.eStore.dto.responses.AddProductResponse;
import africa.Semicolon.eStore.dto.responses.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.SimpleErrors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest
public class InventoryControllersTest {
    @Autowired
    private InventoryControllers inventoryControllers;
    @Autowired
    private UserControllers userControllers;
    @Autowired
    private Inventory inventory;
    @Autowired
    private Users users;

    private final Object object = new Object();
    private final Errors errors = new SimpleErrors(object);
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

    private static void assertIsSuccessful(ResponseEntity<?> response, boolean expected) {
        if (response.hasBody() && response.getBody() instanceof ApiResponse apiResponse) {
            boolean success = apiResponse.isSuccessful();
            assertThat(success, is(expected));
        }
    }

    private static String getProductId(ResponseEntity<?> response) {
        if (response.getBody() instanceof ApiResponse apiResponse) {
            if (apiResponse.getData() instanceof AddProductResponse addProductResponse)
                return addProductResponse.getProductId();
        }
        throw new IllegalArgumentException("Error");
    }

    @Test
    public void testFindProductWith_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var addProductResponse = userControllers.addProduct(addProductRequest, errors);
        findProductRequest.setProductId(getProductId(addProductResponse));
        var response = inventoryControllers.findProductWith(findProductRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testFindProductWith_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        findProductRequest.setProductId("invalidProductId");
        var response = inventoryControllers.findProductWith(findProductRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(NOT_FOUND));
    }

    @Test
    public void testFindAllProducts_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        userControllers.addProduct(addProductRequest, errors);
        var response = inventoryControllers.findAllProducts();
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testFindAllProducts_isSuccessful_isTrue_BadRequest() {
        userControllers.register(registerRequest, errors);
        var response = inventoryControllers.findAllProducts();
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }
}