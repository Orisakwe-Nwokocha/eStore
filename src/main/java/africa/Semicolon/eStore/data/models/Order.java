package africa.Semicolon.eStore.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document("Orders")
public final class Order {
    @Id
    private String id;
    @DBRef
    private User buyer;
    private int numberOfItems;
    private String items;
    private double totalPrice;
    private LocalDateTime orderDate = LocalDateTime.now();

    @Override
    public String toString() {
        String asterisk = "*".repeat(15);
        String dateOfOrder = orderDate.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a"));
        String format = "%s%nOrder Id: %s%nNumber of items: %s%nItems: %sTotal Price: ₦%,.2f %nOrder Date: %s%n%s";
        return String.format(format, asterisk, id, numberOfItems, items, totalPrice, dateOfOrder, asterisk);
    }
}
