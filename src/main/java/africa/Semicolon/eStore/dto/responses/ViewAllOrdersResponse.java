package africa.Semicolon.eStore.dto.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class ViewAllOrdersResponse {
    private String username;
    private List<ViewOrderResponse> orders = new ArrayList<>();
}
