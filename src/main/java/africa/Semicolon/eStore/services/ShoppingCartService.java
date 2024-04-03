package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Item;
import africa.Semicolon.eStore.data.models.ShoppingCart;
import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.dtos.requests.AddItemRequest;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addToCartWith(AddItemRequest addItemRequest, User user);
    ShoppingCart removeFromCart(RemoveItemRequest removeItemRequest, User user);
    List<Item> viewCart(User user);
}
