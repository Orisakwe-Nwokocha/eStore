package africa.Semicolon.eStore.services.impls;

import africa.Semicolon.eStore.data.models.*;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dto.requests.*;
import africa.Semicolon.eStore.dto.responses.*;
import africa.Semicolon.eStore.exceptions.*;
import africa.Semicolon.eStore.services.CheckoutServices;
import africa.Semicolon.eStore.services.InventoryServices;
import africa.Semicolon.eStore.services.ShoppingCartServices;
import africa.Semicolon.eStore.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.Semicolon.eStore.data.constants.Role.ADMIN;
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
    @Autowired
    private CheckoutServices checkoutServices;

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
        validateLoginStatusOf(foundUser);
        validate(foundUser);
        return inventoryServices.addProductWith(addProductRequest);
    }

    @Override
    public AddItemResponse addToCart(AddItemRequest addItemRequest) {
        User foundUser = findUserBy(addItemRequest.getUsername());
        validateLoginStatusOf(foundUser);
        ShoppingCart shoppingCart = shoppingCartServices.addToCartWith(addItemRequest, foundUser);
        foundUser.setCart(shoppingCart);
        User savedUser = users.save(foundUser);
        return mapAddItemResponse(savedUser);
    }

    @Override
    public RemoveItemResponse removeFromCart(RemoveItemRequest removeItemRequest) {
        User foundUser = findUserBy(removeItemRequest.getUsername());
        validateLoginStatusOf(foundUser);
        ShoppingCart shoppingCart = shoppingCartServices.removeFromCartWith(removeItemRequest, foundUser);
        foundUser.setCart(shoppingCart);
        User savedUser = users.save(foundUser);
        return mapRemoveItemResponse(savedUser);
    }

    @Override
    public ViewCartResponse viewCart(ViewCartRequest viewCartRequest) {
        User foundUser = findUserBy(viewCartRequest.getUsername());
        validateLoginStatusOf(foundUser);
        if (foundUser.getCart().getItems().isEmpty()) throw new ShoppingCartIsEmptyException("Your cart is empty");
        return mapViewCartResponse(foundUser);
    }

    @Override
    public UpdateDeliveryDetailsResponse updateDeliveryDetails(UpdateDeliveryDetailsRequest updateDeliveryDetailsRequest) {
        User foundUser = findUserBy(updateDeliveryDetailsRequest.getUsername());
        validateLoginStatusOf(foundUser);
        BillingInformation updatedBillingInformation = map(updateDeliveryDetailsRequest, foundUser.getBillingInformation());
        foundUser.setBillingInformation(updatedBillingInformation);
        User savedUser = users.save(foundUser);
        return mapUpdateDeliveryDetailsResponse(savedUser);
    }

    @Override
    public UpdateCreditCardInfoResponse updateCreditCardInfo(UpdateCreditCardInfoRequest updateCreditCardInfoRequest) {
        User foundUser = findUserBy(updateCreditCardInfoRequest.getUsername());
        validateLoginStatusOf(foundUser);
        CreditCardInformation creditCardInfo = map(updateCreditCardInfoRequest, foundUser.getBillingInformation().getCreditCardInfo());
        foundUser.getBillingInformation().setCreditCardInfo(creditCardInfo);
        User savedUser = users.save(foundUser);
        return mapUpdateCreditCardInfoResponse(savedUser);
    }

    @Override
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {
        User foundUser = findUserBy(checkoutRequest.getUsername());
        validateLoginStatusOf(foundUser);
        Order newOrder = checkoutServices.placeOrder(foundUser);
        foundUser.getOrders().add(newOrder);
        foundUser.getCart().getItems().clear();
        users.save(foundUser);
        return mapCheckoutResponse(newOrder);
    }

    @Override
    public ViewOrderResponse viewOrder(ViewOrderRequest viewOrderRequest) {
        User foundUser = findUserBy(viewOrderRequest.getUsername());
        validateLoginStatusOf(foundUser);
        Order foundOrder = findOrderBy(viewOrderRequest.getOrderId(), foundUser.getOrders());
        return mapViewOrderResponse(foundOrder);
    }

    @Override
    public ViewAllOrdersResponse viewAllOrders(ViewAllOrdersRequest viewAllOrdersRequest) {
        User foundUser = findUserBy(viewAllOrdersRequest.getUsername());
        validateLoginStatusOf(foundUser);
        if (foundUser.getOrders().isEmpty()) throw new IllegalUserStateException("You have not placed any order yet");
        return mapViewAllOrdersResponse(foundUser.getOrders());
    }

    private Order findOrderBy(String orderId, List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst().orElseThrow(()-> new OrderNotFoundException("Order not found"));
    }

    private void validate(User user) {
        boolean isAdmin = user.getRole().equals(ADMIN);
        if (!isAdmin) throw new InvalidUserRoleException("User is not a valid admin");
    }

    private void validateLoginStatusOf(User user) {
        if (!user.isLoggedIn()) throw new IllegalUserStateException("User is not logged in");
    }

    private User findUserBy(String username) {
        username = lowerCaseValueOf(username);
        User foundUser = users.findByUsername(username);
        if (foundUser == null) throw new UserNotFoundException(String.format("User with '%s' not found", username));
        return foundUser;
    }

    private void validateUniqueUsername(RegisterRequest registerRequest) {
        String username = lowerCaseValueOf(registerRequest.getUsername());
        boolean userExists = users.existsByUsername(username);
        if (userExists) throw new UserExistsException(String.format("%s already exists", username));
    }

    private void validateBlank(RegisterRequest registerRequest) {
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
