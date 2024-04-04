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

    @Override
    public String toString() {
        String format = "%n{%n\tCard Holder Name: %s%n\tExpiration Date: %s/%s%n\tCard Type: %s%n}";
        return String.format(format, cardHolderName, cardExpirationMonth, cardExpirationYear, cardType);
    }
}
