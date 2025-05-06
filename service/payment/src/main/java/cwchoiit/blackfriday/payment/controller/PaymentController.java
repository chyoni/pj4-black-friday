package cwchoiit.blackfriday.payment.controller;

import cwchoiit.blackfriday.payment.service.PaymentService;
import cwchoiit.blackfriday.payment.service.request.ProcessPaymentRequest;
import cwchoiit.blackfriday.payment.service.request.RegisterPaymentMethodRequest;
import cwchoiit.blackfriday.payment.service.response.PaymentMethodReadResponse;
import cwchoiit.blackfriday.payment.service.response.PaymentReadResponse;
import cwchoiit.blackfriday.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/methods")
    public ResponseEntity<ApiResponse<PaymentMethodReadResponse>> registerPaymentMethod(@RequestBody RegisterPaymentMethodRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(paymentService.registerPaymentMethod(request)));
    }

    @GetMapping("/methods/members/{memberId}")
    public ResponseEntity<ApiResponse<List<PaymentMethodReadResponse>>> findPaymentMethodByUser(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(ApiResponse.ok(paymentService.findPaymentMethodByUser(memberId)));
    }

    @PostMapping("/process-payment")
    public ResponseEntity<ApiResponse<PaymentReadResponse>> processPayment(@RequestBody ProcessPaymentRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(paymentService.processPayment(request)));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentReadResponse>> findPayment(@PathVariable("paymentId") Long paymentId) {
        return ResponseEntity.ok(ApiResponse.ok(paymentService.findPayment(paymentId)));
    }
}
