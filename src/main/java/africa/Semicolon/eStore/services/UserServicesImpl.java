package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.ShoppingCart;
import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dtos.requests.*;
import africa.Semicolon.eStore.dtos.responses.*;
import africa.Semicolon.eStore.exceptions.*;
import africa.Semicolon.eStore.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.Semicolon.eStore.data.models.Role.ADMIN;
import static africa.Semicolon.eStore.utils.Cleaner.lowerCaseValueOf;
import static africa.Semicolon.eStore.utils.Cryptography.isMatches;
import static africa.Semicolon.eStore.utils.Mapper.*;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private Users users;
    @Autowired
    private InventoryServices inventoryServices;
    @Autowired
    private ShoppingCartServices shoppingCartServices;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        validate(registerRequest);
        User newUser = map(registerRequest);
        User savedUser = users.save(newUser);
        return mapRegisterResponseWith(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User foundUser = findUserBy(loginRequest.getUsername());
        if (!isMatches(loginRequest, foundUser)) throw new IncorrectPasswordException("Password is not correct");
        foundUser.setLoggedIn(true);
        User savedUser = users.save(foundUser);
        return mapLoginResponseWith(savedUser);
    }

    @Override
    public LogoutResponse logout(LogoutRequest logOutRequest) {
        User foundUser = findUserBy(logOutRequest.getUsername());
        foundUser.setLoggedIn(false);
        User savedUser = users.save(foundUser);
        return mapLogoutResponseWith(savedUser);
    }

    @Override
    public AddProductResponse addProduct(AddProductRequest addProductRequest) {
        User foundUser = findUserBy(addProductRequest.getUsername());
        validate(foundUser);
        return inventoryServices.addProductWith(addProductRequest);
    }

    @Override
    public AddItemResponse addToCart(AddItemRequest addItemRequest) {
        User foundUser = findUserBy(addItemRequest.getUsername());
        ShoppingCart shoppingCart = shoppingCartServices.addToCartWith(addItemRequest, foundUser);
        foundUser.setCart(shoppingCart);
        User savedUser = users.save(foundUser);
        return mapAddItemResponse(savedUser);
    }

    @Override
    public RemoveItemResponse addToCart(RemoveItemRequest removeItemRequest) {
        User foundUser = findUserBy(removeItemRequest.getUsername());
        ShoppingCart shoppingCart = shoppingCartServices.removeFromCart(removeItemRequest, foundUser);
        foundUser.setCart(shoppingCart);
        User savedUser = users.save(foundUser);
        return mapRemoveItemResponse(savedUser);
    }

    @Override
    public ViewCartResponse viewCart(ViewCartRequest viewCartRequest) {
        User foundUser = findUserBy(viewCartRequest.getUsername());
        if (foundUser.getCart().getItems().isEmpty()) throw new ShoppingCartIsEmptyException("Your cart is empty");
        return mapViewCartResponse(foundUser);
    }

    private void validate(User user) {
        boolean isAdmin = user.getRole().equals(ADMIN);
        if (!isAdmin) throw new InvalidArgumentException("User is not a valid admin");
    }

    private void validateLoginStatusOf(User user) {
        if (!user.isLoggedIn()) throw new IllegalUserStateException("User is not logged in");
    }

    private User findUserBy(String username) {
        username = lowerCaseValueOf(username);
        User foundUser = users.findByUsername(username);
        if (foundUser == null) throw new UserNotFoundException(String.format("User with '%s' username not found", username));
        return foundUser;
    }

    private void validateUniqueUsername(RegisterRequest registerRequest) {
        String username = lowerCaseValueOf(registerRequest.getUsername());
        boolean userExists = users.existsByUsername(username);
        if (userExists) throw new UserExistsException(String.format("%s already exists", username));
    }

    private static void validateBlank(RegisterRequest registerRequest) {
        boolean isBlank = registerRequest.getUsername().isBlank()
                || registerRequest.getPassword().isBlank()
                || registerRequest.getRole().isBlank();
        if (isBlank) throw new InvalidArgumentException("Registration details cannot be blank");
    }

    private void validate(RegisterRequest registerRequest) {
        validateBlank(registerRequest);
        validateUniqueUsername(registerRequest);
    }
}
