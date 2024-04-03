package africa.Semicolon.eStore.data.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class ShoppingCart {
    private final List<Item> items = new ArrayList<>();
}
