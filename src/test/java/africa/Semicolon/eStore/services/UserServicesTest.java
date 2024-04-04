package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dtos.requests.*;
import africa.Semicolon.eStore.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserServicesTest {
    @Autowired
    private UserServices userServices;
    @Autowired
    private Users users;
    @Autowired
    private Inventory inventory;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private AddProductRequest addProductRequest;
    private AddItemRequest addItemRequest;
    private ViewCartRequest viewCartRequest;
    private UpdateDeliveryDetailsRequest updateDeliveryDetailsRequest;

    @BeforeEach
    public void setUp() {
        users.deleteAll();
        inventory.deleteAll();

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setRole("customer");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        addProductRequest = new AddProductRequest();
        addProductRequest.setUsername("username2");
        addProductRequest.setName("pixel 6");
        addProductRequest.setDescription("smartphone");
        addProductRequest.setQuantity(10);
        addProductRequest.setCategory("electronics");
        addProductRequest.setPrice(350_000);

        addItemRequest = new AddItemRequest();
        addItemRequest.setUsername("username");
        addItemRequest.setQuantityOfProduct(3);

        viewCartRequest = new ViewCartRequest();
        viewCartRequest.setUsername("username");

        updateDeliveryDetailsRequest = new UpdateDeliveryDetailsRequest();
        updateDeliveryDetailsRequest.setUsername("username");
        updateDeliveryDetailsRequest.setReceiverName("receiverName");
        updateDeliveryDetailsRequest.setStreet("streetName");

    }
    @Test
    public void registerUser_numberOfUsersIsOneTest() {
        assertThat(users.count(), is(0L));
        var registerResponse = userServices.register(registerRequest);
        assertThat(users.count(), is(1L));
        assertThat(registerResponse.getId(), notNullValue());
    }

    @Test
    public void registerSameUser_throwsUserExistsExceptionTest() {
        userServices.register(registerRequest);
        try {
            userServices.register(registerRequest);
        }
        catch (UserExistsException e) {
            assertThat(e.getMessage(), containsString("username already exists"));
        }
        assertThat(users.count(), is(1L));
    }

    @Test
    public void loginUserWithCorrectPassword_loginUserResponseIsNotNull() {
        userServices.register(registerRequest);
        assertThat(users.count(), is(1L));
        var loginResponse = userServices.login(loginRequest);
        assertThat(loginResponse.getId(), notNullValue());
        assertThat(loginResponse.isLoggedIn(), is(true));
    }

    @Test
    public void loginNonExistentUser_throwsUserNotFoundExceptionTest() {
        userServices.register(registerRequest);
        loginRequest.setUsername("Non existent username");
        try {
            userServices.login(loginRequest);
        }
        catch (UserNotFoundException e) {
            assertThat(e.getMessage(), containsString("User with 'non existent username' not found"));
        }
    }

    @Test
    public void loginWithIncorrectPassword_throwsIncorrectPasswordExceptionTest() {
        userServices.register(registerRequest);
        loginRequest.setPassword("incorrectPassword");
        try {
            userServices.login(loginRequest);
        }
        catch (IncorrectPasswordException e) {
            assertThat(e.getMessage(), containsString("Password is not correct"));
        }
    }
    @Test
    public void logoutTest() {
        userServices.register(registerRequest);
        assertThat(users.count(), is(1L));

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        var logoutResponse = userServices.logout(logoutRequest);
        assertThat(logoutResponse.getId(), notNullValue());
        assertThat(logoutResponse.isLoggedIn(), is(false));
    }

    @Test
    public void addProduct_numberOfProductsIs1Test() {
        assertThat(inventory.count(), is(0L));
        registerRequest.setUsername("username2");
        registerRequest.setRole("admin");
        userServices.register(registerRequest);

        var addProductResponse = userServices.addProduct(addProductRequest);
        assertThat(inventory.count(), is(1L));
        assertThat(addProductResponse.getProductId(), notNullValue());
    }

    @Test
    public void givenInvalidUserRole_addProduct_throwsInvalidUserRoleException_numberOfProductsIs0Test() {
        assertThat(inventory.count(), is(0L));
        registerRequest.setUsername("username2");
        userServices.register(registerRequest);
        try {
            userServices.addProduct(addProductRequest);
        }
        catch (InvalidUserRoleException e) {
            assertThat(e.getMessage(), containsString("User is not a valid admin"));
        }
        assertThat(inventory.count(), is(0L));
    }

    @Test
    public void addToCart_numberOfItemsInUserCartIs1Test() {
        userServices.register(registerRequest);
        registerRequest.setUsername("username2");
        registerRequest.setRole("admin");
        userServices.register(registerRequest);
        var addProductResponse = userServices.addProduct(addProductRequest);

        var user = users.findByUsername("username");
        assertThat(user.getCart().getItems(), hasSize(0));
        addItemRequest.setProductId(addProductResponse.getProductId());
        var addItemResponse = userServices.addToCart(addItemRequest);
        user = users.findByUsername("username");
        assertThat(user.getCart().getItems(), hasSize(1));
        assertThat(addItemResponse.getShoppingCart(), notNullValue());
    }

    @Test
    public void addSameProductToCart_quantityOfProductAddedIs5_numberOfItemsInUserCartIs1Test() {
        userServices.register(registerRequest);
        registerRequest.setUsername("username2");
        registerRequest.setRole("admin");
        userServices.register(registerRequest);
        var addProductResponse = userServices.addProduct(addProductRequest);
        addItemRequest.setProductId(addProductResponse.getProductId());
        userServices.addToCart(addItemRequest);
        var user = users.findByUsername("username");
        var cart = user.getCart();
        assertThat(cart.getItems(), hasSize(1));
        assertThat(cart.getItems().getFirst().getQuantityOfProduct(), is(3));

        addItemRequest.setQuantityOfProduct(5);
        var addItemResponse = userServices.addToCart(addItemRequest);
        user = users.findByUsername("username");
        cart = user.getCart();
        assertThat(cart.getItems(), hasSize(1));
        assertThat(cart.getItems().getFirst().getQuantityOfProduct(), is(5));
        assertThat(addItemResponse.getShoppingCart(), notNullValue());
    }

    @Test
    public void givenItemInCart_removeItemFromCart_numberOfItemsInUserCartIs0Test() {
        userServices.register(registerRequest);
        registerRequest.setUsername("username2");
        registerRequest.setRole("admin");
        userServices.register(registerRequest);
        var addProductResponse = userServices.addProduct(addProductRequest);
        addItemRequest.setProductId(addProductResponse.getProductId());
        userServices.addToCart(addItemRequest);
        var user = users.findByUsername("username");
        assertThat(user.getCart().getItems(), hasSize(1));

        RemoveItemRequest removeItemRequest = new RemoveItemRequest();
        removeItemRequest.setProductId(addProductResponse.getProductId());
        removeItemRequest.setUsername("username");
        var removeItemResponse = userServices.removeFromCart(removeItemRequest);
        user = users.findByUsername("username");
        assertThat(user.getCart().getItems(), hasSize(0));
        assertThat(removeItemResponse.getShoppingCart(), notNullValue());
    }

    @Test
    public void givenItemInCart_viewCartTest() {
        userServices.register(registerRequest);
        registerRequest.setUsername("username2");
        registerRequest.setRole("admin");
        userServices.register(registerRequest);
        var addProductResponse = userServices.addProduct(addProductRequest);
        addItemRequest.setProductId(addProductResponse.getProductId());
        userServices.addToCart(addItemRequest);

        var viewCartResponse = userServices.viewCart(viewCartRequest);
        assertThat(viewCartResponse.getShoppingCart(), notNullValue());
    }

    @Test
    public void givenEmptyCart_viewCart_throwsShoppingCartIsEmptyExceptionTest() {
        userServices.register(registerRequest);
        var user = users.findByUsername("username");
        assertThat(user.getCart().getItems(), is(empty()));
        try {
            userServices.viewCart(viewCartRequest);
        }
        catch (ShoppingCartIsEmptyException e) {
            assertThat(e.getMessage(), containsString("Your cart is empty"));
        }
        user = users.findByUsername("username");
        assertThat(user.getCart().getItems(), is(empty()));
    }

    @Test
    public void updateDeliveryDetailsTest() {
        userServices.register(registerRequest);
        var updateDeliveryDetailsResponse = userServices.updateDeliveryDetails(updateDeliveryDetailsRequest);
        System.out.println(updateDeliveryDetailsResponse.getBillingInformation());
        assertThat(updateDeliveryDetailsResponse.getBillingInformation(), notNullValue());
    }

    @Test
    public void updateCreditCardInfoTest() {
        userServices.register(registerRequest);
        userServices.updateDeliveryDetails(updateDeliveryDetailsRequest);
        UpdateCreditCardInfoRequest updateCreditCardInfoRequest = new UpdateCreditCardInfoRequest();
        updateCreditCardInfoRequest.setUsername("username");
        updateCreditCardInfoRequest.setCreditCardNumber("37");
        var updateCreditCardInfoResponse = userServices.updateCreditCardInfoResponse(updateCreditCardInfoRequest);
        System.out.println(updateCreditCardInfoResponse.getBillingInformation());
        assertThat(updateCreditCardInfoResponse.getBillingInformation(), notNullValue());
    }
}