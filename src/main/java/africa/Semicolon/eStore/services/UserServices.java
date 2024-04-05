package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.dtos.requests.*;
import africa.Semicolon.eStore.dtos.responses.*;

public interface UserServices {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    LogoutResponse logout(LogoutRequest logOutRequest);
    AddProductResponse addProduct(AddProductRequest addProductRequest);
    AddItemResponse addToCart(AddItemRequest addItemRequest);
    RemoveItemResponse removeFromCart(RemoveItemRequest removeItemRequest);
    ViewCartResponse viewCart(ViewCartRequest viewCartRequest);
    UpdateDeliveryDetailsResponse updateDeliveryDetails(UpdateDeliveryDetailsRequest updateDeliveryDetailsRequest);
    UpdateCreditCardInfoResponse updateCreditCardInfo(UpdateCreditCardInfoRequest updateCreditCardInfoRequest);
    CheckoutResponse checkout(CheckoutRequest checkoutRequest);
    ViewOrderResponse viewOrder(ViewOrderRequest viewOrderRequest);
    ViewAllOrdersResponse viewAllOrders(ViewAllOrdersRequest viewAllOrdersRequest);
}
