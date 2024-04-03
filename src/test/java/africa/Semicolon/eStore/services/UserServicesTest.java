package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dtos.requests.*;
import africa.Semicolon.eStore.exceptions.IncorrectPasswordException;
import africa.Semicolon.eStore.exceptions.InvalidUserRoleException;
import africa.Semicolon.eStore.exceptions.UserExistsException;
import africa.Semicolon.eStore.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

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
    private RemoveItemRequest removeItemRequest;
    private ViewCartRequest viewCartRequest;

    @BeforeEach
    public void setUp() {
        users.deleteAll();

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
        registerRequest.setRole("admin");
        userServices.register(registerRequest);

        var addProductResponse = userServices.addProduct(addProductRequest);
        assertThat(inventory.count(), is(1L));
        assertThat(addProductResponse.getProductId(), notNullValue());
    }

    @Test
    public void givenInvalidUserRole_addProduct_throwsInvalidUserRoleException_numberOfProductsIs0Test() {
        assertThat(inventory.count(), is(0L));
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
    void addToCart() {
    }

    @Test
    void removeFromCart() {
    }

    @Test
    void viewCart() {
    }
}