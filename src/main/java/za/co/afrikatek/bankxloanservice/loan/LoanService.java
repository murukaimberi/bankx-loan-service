package za.co.afrikatek.bankxloanservice.loan;

import java.util.Optional;

interface LoanService {
    Loan createLoan(Loan loan);
    Optional<Loan> getLoanById(Long id);
}
