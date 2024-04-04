package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.*;
import africa.Semicolon.eStore.data.repositories.Orders;
import africa.Semicolon.eStore.exceptions.ShoppingCartIsEmptyException;
import africa.Semicolon.eStore.utils.Mapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.Semicolon.eStore.utils.Mapper.map;

@Service
public class CheckoutServicesImpl implements CheckoutServices {
    @Autowired
    private Orders orders;
    @Autowired
    private InventoryServices inventoryServices;

    @Override
    public Order placeOrder(User user) {
        validate(user.getCart());
        inventoryServices.validate(user.getCart());
        double totalPrice = calculateTotalPrice(user.getCart());
        Order newOrder = map(user, totalPrice);
        inventoryServices.updateProductQuantity(user.getCart().getItems());
        return orders.save(newOrder);
    }

    private double calculateTotalPrice(ShoppingCart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantityOfProduct())
                .sum();
    }

    private void validate(ShoppingCart cart) {
        if (cart.getItems().isEmpty()) throw new ShoppingCartIsEmptyException("Your cart is empty");
    }
}
