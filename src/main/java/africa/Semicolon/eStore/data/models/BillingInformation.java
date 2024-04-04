package africa.Semicolon.eStore.data.models;

import lombok.Data;

@Data
public final class BillingInformation {
    private String receiverName;
    private String receiverPhoneNumber;

    private Address deliveryAddress;
    private CreditCardInformation creditCardInfo;

}
