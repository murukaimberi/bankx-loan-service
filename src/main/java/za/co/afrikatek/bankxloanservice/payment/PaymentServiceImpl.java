/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 17:05
 */

package za.co.afrikatek.bankxloanservice.payment;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.afrikatek.bankxloanservice.exception.Status400BadRequestException;
import za.co.afrikatek.bankxloanservice.loan.LoanService;

import java.util.List;
import java.util.Map;


@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final LoanService loanService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, LoanService loanService) {
        this.paymentRepository = paymentRepository;
        this.loanService = loanService;
    }

    @Transactional
    @Override
    public Payment createPayment(Payment payment) {
        LOG.debug("Request to make a payment to loan : {}", payment);
        if (payment.getPaymentId() != null) {
            throw new Status400BadRequestException("Payment ID must be null", "Payment", Map.of("id", List.of("Payment ID must be null")));
        }
        loanService.updateLoan(payment.getPaymentAmount(), payment.getLoanId());
        return paymentRepository.save(payment);
    }
}
