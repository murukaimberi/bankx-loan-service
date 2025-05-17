package za.co.afrikatek.bankxloanservice.loan;

import java.math.BigDecimal;
import java.util.Optional;

public interface LoanService {
    Loan createLoan(Loan loan);
    Optional<Loan> getLoanById(Long id);
    Loan updateLoan(BigDecimal paymentAmount, Long loanId);
}
