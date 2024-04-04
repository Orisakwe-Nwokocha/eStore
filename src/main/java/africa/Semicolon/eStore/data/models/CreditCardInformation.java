package africa.Semicolon.eStore.data.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class CreditCardInformation {
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

    @NotNull(message = "Card type cannot be null")
    private CardType cardType;

    @Override
    public String toString() {
        String format = "%n{%n\tCard Holder Name: %s%n\tExpiration Date: %s/%s%n\tCard Type: %s%n}";
        return String.format(format, cardHolderName, cardExpirationMonth, cardExpirationYear, cardType);
    }
}
