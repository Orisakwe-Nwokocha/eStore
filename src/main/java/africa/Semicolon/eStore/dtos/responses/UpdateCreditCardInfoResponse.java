package africa.Semicolon.eStore.dtos.responses;

import lombok.Data;

@Data
public final class UpdateCreditCardInfoResponse {
    private String username;
    private String billingInformation;
}
