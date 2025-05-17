/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 17:04
 */

package za.co.afrikatek.bankxloanservice.payment;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("")
    public ResponseEntity<Payment> createPayment(@RequestBody @Valid Payment payment) {
        LOG.info("Rest request to make a payment to loan : {}", payment);
        payment = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}
