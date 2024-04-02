package africa.Semicolon.eStore.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Document("Orders")
public final class Order {
    @Id
    private String id;
    private final int numberOfItems;
    private final String items;
    private final double totalPrice;
    private final String orderDate;
    private static int count = 0;

    public Order(ShoppingCart cart, double totalPrice) {
        orderID = ++count;
        numberOfItems = cart.view().size();
        items = getItems(cart);
        this.totalPrice = totalPrice;
        orderDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
    }

    private static String getItems(ShoppingCart cart) {
        StringBuilder items = new StringBuilder();

        String asterisks = "*".repeat(17);

        items.append("\n").append(asterisks).append("\n");
        for (Item item : cart.view()) items.append(item).append("\n").append(asterisks).append("\n");

        return items.toString();
    }

    @Override
    public String toString() {
        return String.format("""
                Order Id: %d
                Number of items: %s
                Items: %sTotal Price: ₦%,.2f
                Order Date: %s
                """, orderID, numberOfItems, items, totalPrice, orderDate);
    }
}
