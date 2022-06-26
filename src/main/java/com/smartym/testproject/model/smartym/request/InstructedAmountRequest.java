package com.smartym.testproject.model.smartym.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructedAmountRequest {
    private  Double amount;
    private String currency;
}
