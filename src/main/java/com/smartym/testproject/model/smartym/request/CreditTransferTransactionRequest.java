package com.smartym.testproject.model.smartym.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditTransferTransactionRequest {
    private InstructedAmountRequest instructedAmount;
    private RemittanceInformationRequest remittanceInformation;
    private String requestedExecutionDate;
}
