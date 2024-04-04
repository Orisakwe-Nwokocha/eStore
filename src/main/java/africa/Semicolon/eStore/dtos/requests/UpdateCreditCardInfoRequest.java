package africa.Semicolon.eStore.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class UpdateCreditCardInfoRequest {
    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Credit card number cannot be null")
    private String creditCardNumber;

    @NotNull(message = "Card holder name cannot be null")
    private String cardHolderName;

    @NotNull(message = "Card expiration month cannot be null")
    private String cardExpirationMonth;

    @NotNull(message = "Card expiration year cannot be null")
    private String cardExpirationYear;

    @NotNull(message = "CVV cannot be null")
    private String cvv;
}
