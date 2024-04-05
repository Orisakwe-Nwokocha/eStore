package africa.Semicolon.eStore.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public final class ApiResponse {
    private boolean isSuccessful;
    private Object data;
}
