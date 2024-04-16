package africa.Semicolon.eStore.dto.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class ViewOrderResponse {
    private String username;
    private String orderId;
    private int numberOfItems;
    private List<GetItemResponse> items = new ArrayList<>();
    private String totalPrice;
    private String dateOfOrder;
}
