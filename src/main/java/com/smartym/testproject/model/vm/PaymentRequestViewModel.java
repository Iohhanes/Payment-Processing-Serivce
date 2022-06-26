package com.smartym.testproject.model.vm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentRequestViewModel {
    private String creditorIban;
    private String creditorName;
    private Double amount;
    private String currency;
    private String description;
}
