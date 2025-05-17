/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 18:04
 */

package za.co.afrikatek.bankxloanservice.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.co.afrikatek.bankxloanservice.loan.Loan;
import za.co.afrikatek.bankxloanservice.loan.LoanService;
import za.co.afrikatek.bankxloanservice.loan.LoanStatus;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PaymentService paymentService;

    Loan loan;

    @Autowired
    LoanService loanService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ObjectMapper objectMapper;

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
    void whenMakeLoanPayment_Then_Succeed() throws Exception {
        Payment payment = new Payment();
        payment.setLoanId(loan.getLoanId());
        payment.setPaymentAmount(BigDecimal.valueOf(5000));
        Payment savedPayment = objectMapper.readValue(mockMvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(payment)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString(), Payment.class);
        BigDecimal balance = loan.getLoanAmount().subtract(savedPayment.getPaymentAmount());
        Assertions.assertNotNull(savedPayment);
        Assertions.assertNotNull(savedPayment.getPaymentId());
        Assertions.assertEquals(0, BigDecimal.valueOf(5000).compareTo(savedPayment.getPaymentAmount()));
        Assertions.assertEquals(loan.getLoanId(), savedPayment.getLoanId());
        loanService.getLoanById(loan.getLoanId()).ifPresent(loan1 -> Assertions.assertEquals(0, balance.compareTo(loan1.getLoanAmount())));
    }

    @Test
    void whenMakeLoanPayment_And_LoanIdIsNotFound_ThenThrowException() throws Exception {
        Payment payment = new Payment();
        payment.setLoanId(100L);
        payment.setPaymentAmount(BigDecimal.valueOf(5000));
        mockMvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(payment)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void whenMakeLoanPayment_And_LoanAmountIsNegative_ThenThrowException() throws Exception {
        Payment payment = new Payment();
        payment.setLoanId(loan.getLoanId());
        payment.setPaymentAmount(BigDecimal.valueOf(-5000));
        mockMvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(payment)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void whenMakeLoanPayment_And_LoanSettled_ThenThrowException() throws Exception {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setStatus(LoanStatus.SETTLED);
        loan.setTerm(72);
        loan = loanService.createLoan(loan);

        Payment payment = new Payment();
        payment.setLoanId(loan.getLoanId());
        payment.setPaymentAmount(BigDecimal.valueOf(5000));
        mockMvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(payment)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
