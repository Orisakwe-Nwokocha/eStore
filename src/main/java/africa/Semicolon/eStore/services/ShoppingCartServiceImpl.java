package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Item;
import africa.Semicolon.eStore.data.models.Product;
import africa.Semicolon.eStore.data.models.ShoppingCart;
import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.dtos.requests.AddItemRequest;
import africa.Semicolon.eStore.exceptions.InvalidArgumentException;
import africa.Semicolon.eStore.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private InventoryServices inventoryServices;

    @Override
    public ShoppingCart addToCartWith(AddItemRequest addItemRequest, User user) {
        Product product = inventoryServices.findBy(addItemRequest.getProductId());
        validate(addItemRequest.getQuantityOfProduct());
        ShoppingCart shoppingCart = user.getCart();
        if (isPresent(product, shoppingCart)) update(product, addItemRequest.getQuantityOfProduct(), shoppingCart);
        else addNew(addItemRequest)
        return null;
    }

    private void validate(int quantityOfProduct) {
        if (quantityOfProduct <= 0) throw new InvalidArgumentException("Quantity of product must be positive");
    }

    @Override
    public ShoppingCart removeFromCart(RemoveItemRequest removeItemRequest, User user) {
        Product product = inventoryServices.findBy(removeItemRequest.getProductId());
        ShoppingCart shoppingCart = user.getCart();
        Item foundItem = findItemBy(product, shoppingCart);
        shoppingCart.getItems().remove(foundItem);
        return shoppingCart;
    }


    @Override
    public List<Item> viewCart(User user) {
        return null;
    }

    private void addNew(AddItemRequest addItemRequest, ShoppingCart shoppingCart) {
        Product product = inventoryServices.findBy(addItemRequest.getProductId());
        Item newItem = new Item(product, addItemRequest.getQuantityOfProduct());
        shoppingCart.getItems().add(newItem);
    }

    private void update(Product product, int quantityOfProduct, ShoppingCart shoppingCart) {
        Item foundItem = findItemBy(product, shoppingCart);
        foundItem.setQuantityOfProduct(quantityOfProduct);
    }

    private Item findItemBy(Product product, ShoppingCart cart) {
        for (Item item : cart.getItems()) if (item.getProduct().equals(product)) return item;
        throw new ItemNotFoundException("Item is not in the cart.");
    }

    private boolean isPresent(Product product, ShoppingCart cart) {
        for (Item item : cart.getItems()) if (item.getProduct().equals(product)) return true;
        return false;
    }
}
