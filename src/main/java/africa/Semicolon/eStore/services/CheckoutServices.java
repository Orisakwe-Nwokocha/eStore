package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Order;
import africa.Semicolon.eStore.data.models.User;

public interface CheckoutServices {
    Order placeOrder(User user);
}
