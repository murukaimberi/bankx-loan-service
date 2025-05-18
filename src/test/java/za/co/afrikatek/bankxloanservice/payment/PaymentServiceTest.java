/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 17:31
 */

package za.co.afrikatek.bankxloanservice.payment;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.afrikatek.bankxloanservice.exception.Status400BadRequestException;
import za.co.afrikatek.bankxloanservice.loan.Loan;
import za.co.afrikatek.bankxloanservice.loan.LoanService;
import za.co.afrikatek.bankxloanservice.loan.LoanStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    LoanService loanService;

    @Autowired
    PaymentRepository paymentRepository;

    Loan loan;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
        loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(72);
        loan.setStatus(LoanStatus.ACTIVE);
        loan = loanService.createLoan(loan);
    }

    @Test
    void whenMakePayment_Then_Succeed() throws Exception {
        Payment payment = new Payment();
        payment.setLoanId(loan.getLoanId());
        payment.setPaymentAmount(BigDecimal.valueOf(5000));
        Payment savedPayment = paymentService.createPayment(payment);
        BigDecimal balance = loan.getLoanAmount().subtract(savedPayment.getPaymentAmount());
        Assertions.assertNotNull(savedPayment);
        Assertions.assertNotNull(savedPayment.getPaymentId());
        Assertions.assertEquals(BigDecimal.valueOf(5000), savedPayment.getPaymentAmount());
        Assertions.assertEquals(loan.getLoanId(), savedPayment.getLoanId());

        Optional<Loan> optionalLoan = loanService.getLoanById(payment.getLoanId());
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(0, balance.compareTo(optionalLoan.get().getLoanAmount()));
    }

    @Test
    void whenMakePayment_And_LoanIdIsNotFound_ThenThrowException() throws Exception {
        Payment payment = new Payment();
        payment.setLoanId(1L);
        payment.setPaymentAmount(BigDecimal.valueOf(5000));
        Assertions.assertThrows(Status400BadRequestException.class, () -> paymentService.createPayment(payment));
    }

    @Test
    void whenMakePayment_And_LoanAmountIsNegative_ThenThrowException() throws Exception {
        Payment payment = new Payment();
        payment.setLoanId(loan.getLoanId());
        payment.setPaymentAmount(BigDecimal.valueOf(-5000));
        Assertions.assertThrows(ConstraintViolationException.class, () -> paymentService.createPayment(payment));
    }

    @Test
    void whenMakePayment_And_Amount_Settles_Loan_ThenLoanAmountZero() throws Exception {
        Payment payment = new Payment();
        payment.setLoanId(loan.getLoanId());
        payment.setPaymentAmount(loan.getLoanAmount());
        Payment savedPayment = paymentService.createPayment(payment);
        Assertions.assertNotNull(savedPayment);
        Assertions.assertNotNull(savedPayment.getPaymentId());
        Assertions.assertEquals(payment.getPaymentAmount(), savedPayment.getPaymentAmount());
        Assertions.assertEquals(loan.getLoanId(), savedPayment.getLoanId());
        Optional<Loan> optionalLoan = loanService.getLoanById(payment.getLoanId());
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(LoanStatus.SETTLED, optionalLoan.get().getStatus());
        Assertions.assertEquals(0, BigDecimal.ZERO.compareTo(optionalLoan.get().getLoanAmount()));
    }
}
