package com.smartym.testproject.service.impl;

import com.smartym.testproject.client.SmartymRestClient;
import com.smartym.testproject.model.smartym.request.*;
import com.smartym.testproject.model.smartym.response.PaymentInitiationResponse;
import com.smartym.testproject.model.vm.PaymentRequestViewModel;
import com.smartym.testproject.properties.AppProperties;
import com.smartym.testproject.security.SecurityUtils;
import com.smartym.testproject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final AppProperties appProperties;
    private final SecurityUtils securityUtils;

    private SmartymRestClient restClient;

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(appProperties.getSmartym().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restClient = retrofit.create(SmartymRestClient.class);
    }

    @Override
    public PaymentInitiationResponse doPaymentRequest(PaymentRequestViewModel paymentRequestData) throws IOException {
        PaymentRequest.PaymentRequestBuilder paymentRequestBuilder = PaymentRequest.builder()
                .beneficiary(BeneficiaryRequest.builder()
                        .creditor(CreditorRequest.builder()
                                .name(paymentRequestData.getCreditorName())
                                .build())
                        .creditorAccount(CreditorAccount.builder()
                                .iban(paymentRequestData.getCreditorIban())
                                .build())
                        .build())
                .creationDateTime(LocalDateTime.now().toString())
                .creditTransferTransaction(CreditTransferTransactionRequest.builder()
                        .instructedAmount(InstructedAmountRequest.builder()
                                .amount(paymentRequestData.getAmount())
                                .currency(paymentRequestData.getCurrency())
                                .build())
                        .remittanceInformation(RemittanceInformationRequest.builder()
                                .unstructured(paymentRequestData.getDescription())
                                .build())
                        .requestedExecutionDate(new Date().toString())
                        .build())
                .numberOfTransactions(0)
                .paymentInformationId(UUID.randomUUID().toString());

        Call<PaymentInitiationResponse> paymentCall = restClient.doPaymentRequest(paymentRequestBuilder.build(), securityUtils.getToken());

        Response<PaymentInitiationResponse> response = paymentCall.execute();

        return response.body();
    }
}
