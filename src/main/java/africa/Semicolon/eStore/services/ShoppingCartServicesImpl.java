package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Item;
import africa.Semicolon.eStore.data.models.Product;
import africa.Semicolon.eStore.data.models.ShoppingCart;
import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.dtos.requests.AddItemRequest;
import africa.Semicolon.eStore.dtos.requests.RemoveItemRequest;
import africa.Semicolon.eStore.exceptions.InvalidArgumentException;
import africa.Semicolon.eStore.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServicesImpl implements ShoppingCartServices {
    @Autowired
    private InventoryServices inventoryServices;

    @Override
    public ShoppingCart addToCartWith(AddItemRequest addItemRequest, User user) {
        Product product = inventoryServices.findBy(addItemRequest.getProductId());
        inventoryServices.validate(addItemRequest.getQuantityOfProduct(), product);

        ShoppingCart shoppingCart = user.getCart();
        if (isPresent(product, shoppingCart)) updateQuantityOf(product, addItemRequest.getQuantityOfProduct(), shoppingCart);
        else addNewItemWith(addItemRequest, shoppingCart);
        return shoppingCart;
    }


    @Override
    public ShoppingCart removeFromCartWith(RemoveItemRequest removeItemRequest, User user) {
        Product product = inventoryServices.findBy(removeItemRequest.getProductId());
        ShoppingCart shoppingCart = user.getCart();
        Item foundItem = findItemBy(product, shoppingCart);
        shoppingCart.getItems().remove(foundItem);
        return shoppingCart;
    }

    private void addNewItemWith(AddItemRequest addItemRequest, ShoppingCart shoppingCart) {
        Product product = inventoryServices.findBy(addItemRequest.getProductId());
        Item newItem = new Item();
        newItem.setProduct(product);
        newItem.setQuantityOfProduct(addItemRequest.getQuantityOfProduct());
        shoppingCart.getItems().add(newItem);
    }

    private void updateQuantityOf(Product product, int quantityOfProduct, ShoppingCart shoppingCart) {
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
