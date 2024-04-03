package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.ShoppingCart;
import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.dtos.requests.AddItemRequest;
import africa.Semicolon.eStore.dtos.requests.RemoveItemRequest;

public interface ShoppingCartServices {
    ShoppingCart addToCartWith(AddItemRequest addItemRequest, User user);
    ShoppingCart removeFromCart(RemoveItemRequest removeItemRequest, User user);
}
