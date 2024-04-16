package africa.Semicolon.eStore.data.models;

import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Data
public final class ShoppingCart {
    @Lazy
    private List<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        String asterisk = "*".repeat(15);
        return String.format("%s%n%s%n%s", asterisk, items, asterisk);
    }
}
