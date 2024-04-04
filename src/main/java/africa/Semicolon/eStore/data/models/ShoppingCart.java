package africa.Semicolon.eStore.data.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class ShoppingCart {
    private List<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        String asterisk = "*".repeat(15);
        return String.format("%s%n%s%n%s", asterisk, items, asterisk);
    }
}
