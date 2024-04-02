package africa.Semicolon.eStore.data.models;

import java.util.ArrayList;
import java.util.List;

public final class Customer extends User {
    private BillingInformation billingInformation;
    private ShoppingCart cart = new ShoppingCart();
    private final List<Order> orders = new ArrayList<>();


    public List<Item> viewCart() {
        return cart.view();
    }

    public void addToCart(Product product, int quantity) {
        Inventory.validateQuantityOf(product, quantity);

        cart.add(product, quantity);
    }

    public void removeFromCart(int productId) {
        cart.remove(productId);
    }

    public void checkout() {
        Order newOrder = Checkout.placeOrder(cart, billingInformation);
        orders.add(newOrder);

        Inventory.updateProductQuantity(cart);

        cart = new ShoppingCart();
    }

    public void setBillingInformation(BillingInformation billingInformation) {
        this.billingInformation = billingInformation;
    }

    public List<Order> viewOrders() {
        return orders;
    }

    public BillingInformation getBillingInformation() {
        return billingInformation;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setShoppingCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
