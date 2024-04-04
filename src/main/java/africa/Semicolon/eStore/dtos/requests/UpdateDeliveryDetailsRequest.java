package africa.Semicolon.eStore.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class UpdateDeliveryDetailsRequest {
    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Receiver name cannot be null")
    private String receiverName;

    @NotNull(message = "Receiver phone number cannot be null")
    private String receiverPhoneNumber;

    @NotNull(message = "City name cannot be null")
    private String cityName;

    @NotNull(message = "Country name cannot be null")
    private String countryName;

    @NotNull(message = "House number cannot be null")
    private String houseNumber;

    @NotNull(message = "Street name cannot be null")
    private String street;

    @NotNull(message = "State name cannot be null")
    private String state;
}
