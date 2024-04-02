package africa.Semicolon.eStore.data.models;

import oopEstore.exceptions.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

public final class ShoppingCart {
    private List<Item> items = new ArrayList<>();

    public List<Item> view() {
        return items;
    }

    public void add(Product product, int quantity) {
        if (isPresent(product)) updateQuantity(product, quantity);

        else items.add(new Item(product, quantity));
    }

    private void updateQuantity(Product product, int quantity) {
        Item foundItem = findItemBy(product.getId());
        foundItem.updateQuantityOfProduct(quantity);
    }

    private boolean isPresent(Product product) {
        for (Item item : items) if (item.getProduct().equals(product)) return true;

        return false;
    }

    public void remove(int productId) {
        if (items.isEmpty()) throw new IllegalStateException("Shopping cart is empty");

        Item foundItem = findItemBy(productId);

        items.remove(foundItem);
    }

    private Item findItemBy(int productId) {
        for (Item item : items) {
            Product product = item.getProduct();
            if (product.getId() == productId) return item;
        }

        throw new ItemNotFoundException("Item is not in the cart.");
    }
}
