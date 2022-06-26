package com.smartym.testproject.model.smartym.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditorRequest {
    private String name;
}
