package com.smartym.testproject.model.smartym.request;

import lombok.Data;

@Data
public class PaymentTypeInformationRequest {
    private String categoryPurpose;
    private String localInstrument;
    private String serviceLevel;
}
