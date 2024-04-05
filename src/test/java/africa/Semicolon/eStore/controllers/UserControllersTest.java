package africa.Semicolon.eStore.controllers;

import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dtos.requests.*;
import africa.Semicolon.eStore.dtos.responses.AddProductResponse;
import africa.Semicolon.eStore.dtos.responses.ApiResponse;
import africa.Semicolon.eStore.dtos.responses.CheckoutResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest
public class UserControllersTest {

    @Autowired
    private UserControllers userControllers;

    @Autowired
    private Users users;

    @Autowired
    private Inventory inventory;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private LogoutRequest logoutRequest;
    private AddProductRequest addProductRequest;
    private AddItemRequest addItemRequest;
    private RemoveItemRequest removeItemRequest;
    private ViewCartRequest viewCartRequest;
    private UpdateDeliveryDetailsRequest updateDeliveryDetailsRequest;
    private UpdateCreditCardInfoRequest updateCreditCardInfoRequest;
    private CheckoutRequest checkoutRequest;
    private ViewOrderRequest viewOrderRequest;
    private ViewAllOrdersRequest viewAllOrdersRequest;

    @BeforeEach
    public void setUp() {
        users.deleteAll();
        inventory.deleteAll();

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setRole("admin");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");

        addProductRequest = new AddProductRequest();
        addProductRequest.setUsername("username");
        addProductRequest.setName("pixel 6");
        addProductRequest.setDescription("smartphone");
        addProductRequest.setQuantity(10);
        addProductRequest.setCategory("electronics");
        addProductRequest.setPrice(350_000);

        addItemRequest = new AddItemRequest();
        addItemRequest.setUsername("username");
        addItemRequest.setQuantityOfProduct(3);

        removeItemRequest = new RemoveItemRequest();
        removeItemRequest.setUsername("username");

        viewCartRequest = new ViewCartRequest();
        viewCartRequest.setUsername("username");

        updateDeliveryDetailsRequest = new UpdateDeliveryDetailsRequest();
        updateDeliveryDetailsRequest.setUsername("username");
        updateDeliveryDetailsRequest.setReceiverName("receiverName");
        updateDeliveryDetailsRequest.setReceiverPhoneNumber("0802");
        updateDeliveryDetailsRequest.setStreet("street");
        updateDeliveryDetailsRequest.setState("state");
        updateDeliveryDetailsRequest.setCityName("cityName");
        updateDeliveryDetailsRequest.setCountryName("countryName");
        updateDeliveryDetailsRequest.setHouseNumber("houseNumber");

        updateCreditCardInfoRequest = new UpdateCreditCardInfoRequest();
        updateCreditCardInfoRequest.setUsername("username");
        updateCreditCardInfoRequest.setCreditCardNumber("37");
        updateCreditCardInfoRequest.setCvv("001");
        updateCreditCardInfoRequest.setCardHolderName("holderName");
        updateCreditCardInfoRequest.setCardExpirationMonth("12");
        updateCreditCardInfoRequest.setCardExpirationYear("2024");

        checkoutRequest = new CheckoutRequest();
        checkoutRequest.setUsername("username");

        viewOrderRequest = new ViewOrderRequest();
        viewOrderRequest.setUsername("username");

        viewAllOrdersRequest = new ViewAllOrdersRequest();
        viewAllOrdersRequest.setUsername("username");
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

    private static String getOrderId(ResponseEntity<?> response) {
        if (response.getBody() instanceof ApiResponse apiResponse) {
            if (apiResponse.getData() instanceof CheckoutResponse checkoutResponse)
                return checkoutResponse.getOrderId();
        }
        throw new IllegalArgumentException("Error");
    }

    @Test
    public void testRegister_isSuccessful_isTrue() {
        var response = userControllers.register(registerRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    public void testRegister_isSuccessful_isFalse() {
        userControllers.register(registerRequest);
        var response = userControllers.register(registerRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testLogin_isSuccessful_isTrue() {
        userControllers.register(registerRequest);
        var response = userControllers.login(loginRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testLogin_isSuccessful_isFalse() {
        userControllers.register(registerRequest);
        loginRequest.setPassword("wrongPassword");
        var response = userControllers.login(loginRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testLogout_isSuccessful_isTrue() {
        userControllers.register(registerRequest);
        var response = userControllers.logout(logoutRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testLogout_isSuccessful_isFalse() {
        userControllers.register(registerRequest);
        logoutRequest.setUsername("nonExistingUsername");
        var response = userControllers.logout(logoutRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testAddProduct_isSuccessful_isTrue() {
        userControllers.register(registerRequest);
        var response = userControllers.addProduct(addProductRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    public void testAddProduct_isSuccessful_isFalse() {
        userControllers.register(registerRequest);
        addProductRequest.setCategory("invalid");
        var response = userControllers.addProduct(addProductRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testAddToCart_isSuccessful_isTrue() {
        userControllers.register(registerRequest);
        var response = userControllers.addProduct(addProductRequest);
        addItemRequest.setProductId(getProductId(response));
        response = userControllers.addToCart(addItemRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testAddToCart_isSuccessful_isFalse() {
        userControllers.register(registerRequest);
        var response = userControllers.addProduct(addProductRequest);
        addItemRequest.setProductId(getProductId(response));
        addItemRequest.setQuantityOfProduct(11);
        response = userControllers.addToCart(addItemRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testRemoveFromCart_isSuccessful_isTrue() {
        userControllers.register(registerRequest);
        var response = userControllers.addProduct(addProductRequest);
        addItemRequest.setProductId(getProductId(response));
        userControllers.addToCart(addItemRequest);
        removeItemRequest.setProductId(getProductId(response));

        response = userControllers.removeFromCart(removeItemRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testRemoveFromCart_isSuccessful_isFalse() {
        userControllers.register(registerRequest);
        var response = userControllers.addProduct(addProductRequest);
        removeItemRequest.setProductId(getProductId(response));

        response = userControllers.removeFromCart(removeItemRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testViewCart_isSuccessful_isTrue() {
        userControllers.register(registerRequest);
        var response = userControllers.addProduct(addProductRequest);
        addItemRequest.setProductId(getProductId(response));
        userControllers.addToCart(addItemRequest);

        response = userControllers.viewCart(viewCartRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testViewCart_isSuccessful_isFalse() {
        userControllers.register(registerRequest);
        var response = userControllers.addProduct(addProductRequest);
        addItemRequest.setProductId(getProductId(response));

        response = userControllers.viewCart(viewCartRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(NO_CONTENT));
    }

    @Test
    public void testUpdateDeliveryDetails_isSuccessful_isTrue() {
    }

    @Test
    public void testUpdateDeliveryDetails_isSuccessful_isFalse() {
    }

    @Test
    public void testUpdateCreditCardInfo_isSuccessful_isTrue() {
    }

    @Test
    public void testUpdateCreditCardInfo_isSuccessful_isFalse() {
    }

    @Test
    public void testCheckout_isSuccessful_isTrue() {
    }

    @Test
    public void testCheckout_isSuccessful_isFalse() {
    }

    @Test
    public void testViewOrder_isSuccessful_isTrue() {
    }

    @Test
    public void testViewOrder_isSuccessful_isFalse() {
    }

    @Test
    public void testViewAllOrders_isSuccessful_isTrue() {
    }

    @Test
    public void testViewAllOrders_isSuccessful_isFalse() {
    }
}
