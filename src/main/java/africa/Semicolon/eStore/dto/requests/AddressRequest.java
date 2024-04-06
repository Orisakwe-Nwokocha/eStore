package africa.Semicolon.eStore.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class AddressRequest {
    @NotNull(message = "address is mandatory")
    private String cityName;
    @NotNull(message = "address is mandatory")
    private String countryName;
    @NotNull(message = "address is mandatory")
    private String houseNumber;
    @NotNull(message = "address is mandatory")
    private String street;
    @NotNull(message = "address is mandatory")
    private String state;
}