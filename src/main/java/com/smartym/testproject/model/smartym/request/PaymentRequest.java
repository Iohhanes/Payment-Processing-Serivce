package com.smartym.testproject.model.smartym.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
    private BeneficiaryRequest beneficiary;
    private String creationDateTime;
    private CreditTransferTransactionRequest creditTransferTransaction;
    private Integer numberOfTransactions;
    private String paymentInformationId;
    private PaymentTypeInformationRequest paymentTypeInformation;
}
