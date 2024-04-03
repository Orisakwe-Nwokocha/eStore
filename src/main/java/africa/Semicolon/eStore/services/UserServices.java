package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.dtos.requests.*;
import africa.Semicolon.eStore.dtos.responses.*;

public interface UserServices {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    LogoutResponse logout(LogoutRequest logOutRequest);
    AddProductResponse addProduct(AddProductRequest addProductRequest);
    AddItemResponse addToCart(AddItemRequest addItemRequest);
    RemoveItemResponse addToCart(RemoveItemRequest removeItemRequest);
    ViewCartResponse viewCart(ViewCartRequest viewCartRequest);
}
