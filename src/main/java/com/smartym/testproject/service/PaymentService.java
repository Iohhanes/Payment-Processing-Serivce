package com.smartym.testproject.service;

import com.smartym.testproject.model.smartym.response.PaymentInitiationResponse;
import com.smartym.testproject.model.vm.PaymentRequestViewModel;

import java.io.IOException;

public interface PaymentService {
    PaymentInitiationResponse doPaymentRequest(PaymentRequestViewModel paymentRequestData) throws IOException;
}
