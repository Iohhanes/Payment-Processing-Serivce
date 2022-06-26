package com.smartym.testproject.client;

import com.smartym.testproject.model.smartym.request.PaymentRequest;
import com.smartym.testproject.model.smartym.response.PaymentInitiationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SmartymRestClient {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/payment-requests")
    Call<PaymentInitiationResponse> doPaymentRequest(@Body PaymentRequest paymentRequest,
                                                     @Header("Authorization") String accessToken);
}
