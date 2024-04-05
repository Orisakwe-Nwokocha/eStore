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
    private Integer numberOfItems;
    private String items;
    private Double totalPrice;
    private LocalDateTime dateOfOrder = LocalDateTime.now();

    @Override
    public String toString() {
        String equalsSign = "=".repeat(42);
        String dateOrdered = dateOfOrder.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy 'at' hh:mm:ss a"));
        String format = "%n%s%nOrder Id: %s%nNumber of items: %d%nItems: %s%nTotal Price: ₦%,.2f %nOrder Date: %s%n%s";
        return String.format(format, equalsSign, id, numberOfItems, items, totalPrice, dateOrdered, equalsSign);
    }
}
