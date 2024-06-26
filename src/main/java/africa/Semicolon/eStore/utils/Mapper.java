package africa.Semicolon.eStore.utils;

import africa.Semicolon.eStore.data.constants.CardType;
import africa.Semicolon.eStore.data.constants.ProductCategory;
import africa.Semicolon.eStore.data.constants.Role;
import africa.Semicolon.eStore.data.models.*;
import africa.Semicolon.eStore.dto.requests.AddProductRequest;
import africa.Semicolon.eStore.dto.requests.RegisterRequest;
import africa.Semicolon.eStore.dto.requests.UpdateCreditCardInfoRequest;
import africa.Semicolon.eStore.dto.requests.UpdateDeliveryDetailsRequest;
import africa.Semicolon.eStore.dto.responses.*;
import africa.Semicolon.eStore.exceptions.InvalidArgumentException;
import africa.Semicolon.eStore.exceptions.InvalidCardTypeException;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static africa.Semicolon.eStore.data.constants.CardType.*;
import static africa.Semicolon.eStore.utils.Cleaner.lowerCaseValueOf;
import static africa.Semicolon.eStore.utils.Cleaner.upperCaseValueOf;
import static africa.Semicolon.eStore.utils.Cryptography.encode;

public final class Mapper {
    public static User map(RegisterRequest registerRequest) {
        String username = lowerCaseValueOf(registerRequest.getUsername());
        String password = encode(registerRequest.getPassword());
        String role = upperCaseValueOf(registerRequest.getRole());

        User user = new User();
        try {
            user.setRole(Role.valueOf(role));
        }
        catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Invalid role: " + role);
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setName(registerRequest.getName());
        user.setEmailAddress(registerRequest.getEmailAddress());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        return user;
    }
    public static RegisterResponse mapRegisterResponseWith(User user) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(user.getId());
        registerResponse.setUsername(user.getUsername());
        return registerResponse;
    }

    public static LoginResponse mapLoginResponseWith(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setLoggedIn(true);
        return loginResponse;
    }

    public static LogoutResponse mapLogoutResponseWith(User user) {
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setId(user.getId());
        logoutResponse.setUsername(user.getUsername());
        logoutResponse.setLoggedIn(false);
        return logoutResponse;
    }

    public static Product map(AddProductRequest addProductRequest) {
        String productCategory = upperCaseValueOf(addProductRequest.getCategory());
        Product product = new Product();
        try {
            product.setCategory(ProductCategory.valueOf(productCategory));
        }
        catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Product category is not valid");
        }
        product.setName(addProductRequest.getProductName());
        product.setDescription(addProductRequest.getDescription());
        product.setPrice(addProductRequest.getPrice());
        product.setQuantity(addProductRequest.getQuantity());
        return product;
    }

    public static AddProductResponse mapAddProductResponseWith(Product product) {
        String price = String.format("₦%,.2f", product.getPrice());
        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setProductId(product.getId());
        addProductResponse.setProductName(product.getName());
        addProductResponse.setCategory(product.getCategory().toString());
        addProductResponse.setPrice(price);
        addProductResponse.setQuantity(product.getQuantity());
        addProductResponse.setDescription(product.getDescription());
        return addProductResponse;
    }

    public static RemoveProductResponse mapRemoveProductResponse(String productId) {
        RemoveProductResponse removeProductResponse = new RemoveProductResponse();
        removeProductResponse.setProductId(productId);
        return removeProductResponse;
    }

    public static FindProductResponse mapFindProductResponse(Product foundProduct) {
        FindProductResponse findProductResponse = new FindProductResponse();
        findProductResponse.setProduct(String.valueOf(foundProduct));
        return findProductResponse;
    }

    public static FindAllProductsResponse mapGetProductsResponse(List<Product> allProducts) {
        FindAllProductsResponse getProductsResponse = new FindAllProductsResponse();
        getProductsResponse.setProducts(allProducts.toString());
        return getProductsResponse;
    }

    public static AddItemResponse mapAddItemResponse(User user) {
        AddItemResponse addItemResponse = new AddItemResponse();
        addItemResponse.setUsername(user.getUsername());
        addItemResponse.setShoppingCart(user.getCart());
        return addItemResponse;
    }

    public static RemoveItemResponse mapRemoveItemResponse(User user) {
        RemoveItemResponse removeItemResponse = new RemoveItemResponse();
        removeItemResponse.setUsername(user.getUsername());
        removeItemResponse.setShoppingCart(user.getCart());
        return removeItemResponse;
    }

    public static ViewCartResponse mapViewCartResponse(User user) {
        ViewCartResponse viewCartResponse = new ViewCartResponse();
        viewCartResponse.setUsername(user.getUsername());
        viewCartResponse.setShoppingCart(user.getCart());
        return viewCartResponse;
    }

    public static BillingInformation map(UpdateDeliveryDetailsRequest updateDeliveryDetailsRequest, BillingInformation billingInformation) {
        billingInformation.setReceiverName(updateDeliveryDetailsRequest.getReceiverName());
        billingInformation.setReceiverPhoneNumber(updateDeliveryDetailsRequest.getReceiverPhoneNumber());

        Address deliveryAddress = billingInformation.getDeliveryAddress();
        deliveryAddress.setStreet(updateDeliveryDetailsRequest.getStreet());
        deliveryAddress.setCityName(updateDeliveryDetailsRequest.getCityName());
        deliveryAddress.setState(updateDeliveryDetailsRequest.getState());
        deliveryAddress.setCountryName(updateDeliveryDetailsRequest.getCountryName());
        deliveryAddress.setHouseNumber(updateDeliveryDetailsRequest.getHouseNumber());

        return billingInformation;
    }

    public static UpdateDeliveryDetailsResponse mapUpdateDeliveryDetailsResponse(User user) {
        UpdateDeliveryDetailsResponse updateDeliveryDetailsResponse = new UpdateDeliveryDetailsResponse();
        updateDeliveryDetailsResponse.setUsername(user.getUsername());
        updateDeliveryDetailsResponse.setBillingInformation(user.getBillingInformation().toString());
        return updateDeliveryDetailsResponse;
    }

    public static CreditCardInformation map(UpdateCreditCardInfoRequest updateCreditCardInfoRequest, CreditCardInformation creditCardInfo) {
        CardType cardType = getCardType(updateCreditCardInfoRequest.getCreditCardNumber());
        creditCardInfo.setCardType(cardType);
        creditCardInfo.setCreditCardNumber(updateCreditCardInfoRequest.getCreditCardNumber());
        creditCardInfo.setCardHolderName(updateCreditCardInfoRequest.getCardHolderName());
        creditCardInfo.setCardExpirationMonth(updateCreditCardInfoRequest.getCardExpirationMonth());
        creditCardInfo.setCardExpirationYear(updateCreditCardInfoRequest.getCardExpirationYear());
        creditCardInfo.setCvv(updateCreditCardInfoRequest.getCvv());
        return creditCardInfo;
    }

    private static CardType getCardType(String creditCardNumber) {
        if (creditCardNumber.length() < 2) throw new InvalidCardTypeException("Card type is not valid");
        char firstDigit = creditCardNumber.charAt(0);
        char secondDigit = creditCardNumber.charAt(1);

        return switch (firstDigit) {
            case '3' -> {if (secondDigit == '7') yield AMERICA_EXPRESS; else throw new InvalidCardTypeException("Card type is not valid");}
            case '4' -> VISA_CARD;
            case '5' -> (secondDigit == '5' && creditCardNumber.length() == 18) ? VERVE : MASTER_CARD;
            default -> throw new InvalidCardTypeException("Card type is not valid");
        };
    }

    public static UpdateCreditCardInfoResponse mapUpdateCreditCardInfoResponse(User user) {
        UpdateCreditCardInfoResponse updateCreditCardInfoResponse = new UpdateCreditCardInfoResponse();
        updateCreditCardInfoResponse.setUsername(user.getUsername());
        updateCreditCardInfoResponse.setBillingInformation(user.getBillingInformation().toString());
        return updateCreditCardInfoResponse;
    }

    public static Order map(User buyer, double totalPrice) {
        Order order = new Order();
        order.setBuyer(buyer);
        order.setNumberOfItems(buyer.getCart().getItems().size());
        order.setItems(buyer.getCart().getItems());
        order.setTotalPrice(totalPrice);
        return order;
    }

    public static CheckoutResponse mapCheckoutResponse(Order newOrder) {
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setUsername(newOrder.getBuyer().getUsername());
        checkoutResponse.setOrderId(newOrder.getId());
        return checkoutResponse;
    }

    public static ViewOrderResponse mapViewOrderResponse(Order order) {
        ViewOrderResponse viewOrderResponse = new ViewOrderResponse();
        viewOrderResponse.setUsername(order.getBuyer().getUsername());
        viewOrderResponse.setOrderId(order.getId());
        viewOrderResponse.setNumberOfItems(order.getNumberOfItems());
        String totalPrice = String.format("₦%,.2f", order.getTotalPrice());
        viewOrderResponse.setTotalPrice(totalPrice);
        String dateOrdered = order.getDateOfOrder().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy 'at' hh:mm:ss a"));
        viewOrderResponse.setDateOfOrder(dateOrdered);
        order.getItems().forEach(item -> {
            GetItemResponse itemResponse = new GetItemResponse();
            itemResponse.setProductId(item.getProduct().getId());
            itemResponse.setProductName(item.getProduct().getName());
            itemResponse.setCategory(item.getProduct().getCategory().toString());
            itemResponse.setDescription(item.getProduct().getDescription());
            String price = String.format("₦%,.2f", item.getProduct().getPrice());
            itemResponse.setPrice(price);
            itemResponse.setQuantityOfProduct(item.getQuantityOfProduct());
            viewOrderResponse.getItems().add(itemResponse);
        });
        return viewOrderResponse;
    }

    public static ViewAllOrdersResponse mapViewAllOrdersResponse(List<Order> orders) {
        ViewAllOrdersResponse viewAllOrdersResponse = new ViewAllOrdersResponse();
        viewAllOrdersResponse.setUsername(orders.getFirst().getBuyer().getUsername());
        orders.forEach(order -> {
            ViewOrderResponse viewOrderResponse = mapViewOrderResponse(order);
            viewAllOrdersResponse.getOrders().add(viewOrderResponse);
        });
        return viewAllOrdersResponse;
    }
}
