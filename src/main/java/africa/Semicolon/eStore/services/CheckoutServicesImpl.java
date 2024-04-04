package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.*;
import africa.Semicolon.eStore.data.repositories.Orders;
import africa.Semicolon.eStore.exceptions.IllegalUserStateException;
import africa.Semicolon.eStore.exceptions.ShoppingCartIsEmptyException;
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
        validate(user.getBillingInformation());
        inventoryServices.validate(user.getCart().getItems());
        double totalPrice = calculateTotalPrice(user.getCart());
        Order newOrder = map(user, totalPrice);
        inventoryServices.updateProductQuantity(user.getCart().getItems());
        return orders.save(newOrder);
    }

    private void validate(BillingInformation billingInformation) {
        checkForNull(billingInformation);
        checkForBlank(billingInformation);
        validate(billingInformation.getDeliveryAddress());
        validate(billingInformation.getCreditCardInfo());
    }

    private void validate(CreditCardInformation creditCardInfo) {
        String[] creditCardInfoArray = {creditCardInfo.getCreditCardNumber(), creditCardInfo.getCardHolderName(),
                creditCardInfo.getCardExpirationMonth(), creditCardInfo.getCardExpirationYear(),
                creditCardInfo.getCvv()};

        for (String str : creditCardInfoArray) if (str == null || str.isBlank())
            throw new IllegalUserStateException("Complete credit card information not provided");
    }

    private void validate(Address deliveryAddress) {
        String[] addressArray = {deliveryAddress.getCityName(), deliveryAddress.getCountryName(),
                deliveryAddress.getHouseNumber(), deliveryAddress.getStreet(), deliveryAddress.getState()};
        
        for (String str : addressArray) if (str == null || str.isBlank())
            throw new IllegalUserStateException("Complete delivery address not provided");
    }

    private static void checkForBlank(BillingInformation billingInfo) {
        boolean isBlank = billingInfo.getReceiverName().isBlank() || billingInfo.getReceiverPhoneNumber().isBlank();
        if (isBlank) throw new IllegalUserStateException("Complete billing information not provided");
    }

    private static void checkForNull(BillingInformation billingInfo) {
        boolean isNull = billingInfo.getReceiverName() == null || billingInfo.getReceiverPhoneNumber() == null;
        if (isNull) throw new IllegalUserStateException("Complete billing information not provided");
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
