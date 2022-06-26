package com.smartym.testproject.web.pages;

import com.smartym.testproject.model.smartym.response.PaymentInitiationResponse;
import com.smartym.testproject.model.vm.PaymentRequestViewModel;
import com.smartym.testproject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentPageController {
    private final PaymentService paymentService;

    @GetMapping
    public String formPaymentRequest(Model model) {
        model.addAttribute("paymentRequest", new PaymentRequestViewModel());
        return "payment";
    }

    @PostMapping
    public String submitPaymentRequest(@ModelAttribute PaymentRequestViewModel paymentRequest, Model model) throws IOException {
        PaymentInitiationResponse response = paymentService.doPaymentRequest(paymentRequest);
        model.addAttribute("status", response.getStatus());
        return "result-payment";
    }
}
