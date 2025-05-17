/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/16
 * Time   : 08:35
 */

package za.co.afrikatek.bankxloanservice.loan;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.afrikatek.bankxloanservice.exception.Status400BadRequestException;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class LoanServiceTest {
    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
    }

    @Test
    void whenCreateLoan_thenSuccess() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        Loan savedLoan = loanService.createLoan(loan);
        Assertions.assertNotNull(savedLoan);
        Assertions.assertNotNull(savedLoan.getLoanId());
        Assertions.assertEquals(BigDecimal.valueOf(500000), savedLoan.getLoanAmount());
        Assertions.assertEquals(36, savedLoan.getTerm());
        Assertions.assertEquals(LoanStatus.ACTIVE, savedLoan.getStatus());
        loanRepository.deleteById(savedLoan.getLoanId());
    }

    @Test
    void whenCreateLoan_And_LoanIdIsGiven_ThenThrowException() {
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        Assertions.assertThrows(Status400BadRequestException.class, () -> loanService.createLoan(loan));
    }

    @Test
    void whenGetLoanById_ThenSuccess() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        Loan savedLoan = loanService.createLoan(loan);
        Assertions.assertNotNull(savedLoan);
        Assertions.assertNotNull(savedLoan.getLoanId());
        Optional<Loan> retrievedLoan = loanService.getLoanById(savedLoan.getLoanId());
        Assertions.assertTrue(retrievedLoan.isPresent());
        Assertions.assertEquals(36, retrievedLoan.get().getTerm());
    }

    @Test
    void whenGetLoanById_And_LoanIdIsNotFound_ThenThrowException() {
        Optional<Loan> optionalLoan = loanService.getLoanById(1L);
        Assertions.assertFalse(optionalLoan.isPresent());
    }

    @Test
    void whenCreateLoan_And_LoanAmountIsNegative_ThenThrowException() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(-500000));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        Assertions.assertThrows(ConstraintViolationException.class, () -> loanService.createLoan(loan));
    }

    @Test
    void whenCreateLoan_And_TermIsNegative_ThenThrowException() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(-36);
        loan.setStatus(LoanStatus.ACTIVE);
        Assertions.assertThrows(ConstraintViolationException.class, () -> loanService.createLoan(loan));
    }

    @Test
    void whenUpdateLoan_ThenSuccess() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        loan = loanService.createLoan(loan);
        loanService.updateLoan(BigDecimal.valueOf(5000), loan.getLoanId());
        Optional<Loan> optionalLoan = loanService.getLoanById(loan.getLoanId());
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(0, BigDecimal.valueOf(495000).compareTo(optionalLoan.get().getLoanAmount()));
        Assertions.assertEquals(LoanStatus.ACTIVE, optionalLoan.get().getStatus());
        loanRepository.deleteById(loan.getLoanId());
    }

    @Test
    void whenUpdateLoan_And_LoanIdIsNotFound_ThenThrowException() {
        Assertions.assertThrows(Status400BadRequestException.class, () -> loanService.updateLoan(BigDecimal.valueOf(5000), 1L));
    }

    @Test
    void whenUpdateLoan_And_LoanIsSettled_ThenThrowException() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.SETTLED);
        loan = loanService.createLoan(loan);
        Loan finalLoan = loan;
        Assertions.assertThrows(Status400BadRequestException.class, () -> loanService.updateLoan(BigDecimal.valueOf(5000), finalLoan.getLoanId()));
    }
}
