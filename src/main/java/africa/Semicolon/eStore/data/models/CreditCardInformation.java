package africa.Semicolon.eStore.data.models;

import lombok.Data;

@Data
public final class CreditCardInformation {
    private String creditCardNumber;
    private String cardHolderName;
    private String cardExpirationMonth;
    private String cardExpirationYear;
    private String cvv;
    private CardType cardType;
}
