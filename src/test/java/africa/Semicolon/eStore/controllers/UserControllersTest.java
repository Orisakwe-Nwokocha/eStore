package africa.Semicolon.eStore.controllers;

import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dto.requests.*;
import africa.Semicolon.eStore.dto.responses.AddProductResponse;
import africa.Semicolon.eStore.dto.responses.ApiResponse;
import africa.Semicolon.eStore.dto.responses.CheckoutResponse;
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
    private final Object object = new Object();
    private final Errors errors = new SimpleErrors(object);

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
        var response = userControllers.register(registerRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    public void testRegister_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.register(registerRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testLogin_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.login(loginRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testLogin_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        loginRequest.setPassword("wrongPassword");
        var response = userControllers.login(loginRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testLogout_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.logout(logoutRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testLogout_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        logoutRequest.setUsername("nonExistingUsername");
        var response = userControllers.logout(logoutRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testAddProduct_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.addProduct(addProductRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    public void testAddProduct_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        addProductRequest.setCategory("invalid");
        var response = userControllers.addProduct(addProductRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testAddToCart_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(response));
        response = userControllers.addToCart(addItemRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testAddToCart_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(response));
        addItemRequest.setQuantityOfProduct(11);
        response = userControllers.addToCart(addItemRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testRemoveFromCart_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(response));
        userControllers.addToCart(addItemRequest, errors);
        removeItemRequest.setProductId(getProductId(response));

        response = userControllers.removeFromCart(removeItemRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testRemoveFromCart_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.addProduct(addProductRequest, errors);
        removeItemRequest.setProductId(getProductId(response));

        response = userControllers.removeFromCart(removeItemRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testViewCart_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(response));
        userControllers.addToCart(addItemRequest, errors);

        response = userControllers.viewCart(viewCartRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testViewCart_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(response));

        response = userControllers.viewCart(viewCartRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(NO_CONTENT));
    }

    @Test
    public void testUpdateDeliveryDetails_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.updateDeliveryDetails(updateDeliveryDetailsRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testUpdateDeliveryDetails_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        userControllers.logout(logoutRequest, errors);
        var response = userControllers.updateDeliveryDetails(updateDeliveryDetailsRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testUpdateCreditCardInfo_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.updateCreditCardInfo(updateCreditCardInfoRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testUpdateCreditCardInfo_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        updateCreditCardInfoRequest.setCreditCardNumber("77");
        var response = userControllers.updateCreditCardInfo(updateCreditCardInfoRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testCheckout_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var addProductResponse = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(addProductResponse));
        userControllers.addToCart(addItemRequest, errors);
        userControllers.updateDeliveryDetails(updateDeliveryDetailsRequest, errors);
        userControllers.updateCreditCardInfo(updateCreditCardInfoRequest, errors);

        var response = userControllers.checkout(checkoutRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testCheckout_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.checkout(checkoutRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testViewOrder_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var addProductResponse = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(addProductResponse));
        userControllers.addToCart(addItemRequest, errors);
        userControllers.updateDeliveryDetails(updateDeliveryDetailsRequest, errors);
        userControllers.updateCreditCardInfo(updateCreditCardInfoRequest, errors);
        var checkoutResponse = userControllers.checkout(checkoutRequest, errors);

        viewOrderRequest.setOrderId(getOrderId(checkoutResponse));
        var response = userControllers.viewOrder(viewOrderRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testViewOrder_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.viewOrder(viewOrderRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void testViewAllOrders_isSuccessful_isTrue() {
        userControllers.register(registerRequest, errors);
        var addProductResponse = userControllers.addProduct(addProductRequest, errors);
        addItemRequest.setProductId(getProductId(addProductResponse));
        userControllers.addToCart(addItemRequest, errors);
        userControllers.updateDeliveryDetails(updateDeliveryDetailsRequest, errors);
        userControllers.updateCreditCardInfo(updateCreditCardInfoRequest, errors);
        userControllers.checkout(checkoutRequest, errors);

        var response = userControllers.viewAllOrders(viewAllOrdersRequest, errors);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void testViewAllOrders_isSuccessful_isFalse() {
        userControllers.register(registerRequest, errors);
        var response = userControllers.viewAllOrders(viewAllOrdersRequest, errors);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(BAD_REQUEST));
    }

}
