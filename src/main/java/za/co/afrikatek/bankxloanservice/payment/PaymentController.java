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

/**
 * REST controller for managing {@link za.co.afrikatek.bankxloanservice.payment.PaymentController}.
 */
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * {@code POST  /payments} : Create a new payment.
     *
     * @param payment the Payment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Payment, or with status
     * {@code 400 (Bad Request)} if the Payment has already an ID, or {@code 400 (Bad Request)} when payment is made for
     * a loan that has already been settled.
     */
    @PostMapping("")
    public ResponseEntity<Payment> createPayment(@RequestBody @Valid Payment payment) {
        LOG.info("Rest request to make a payment to loan : {}", payment);
        payment = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}
