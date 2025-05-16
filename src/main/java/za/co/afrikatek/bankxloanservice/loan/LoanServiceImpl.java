/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/16
 * Time   : 08:19
 */

package za.co.afrikatek.bankxloanservice.loan;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class LoanServiceImpl implements LoanService {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(LoanServiceImpl.class);

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan createLoan(Loan loan) {
        LOG.debug("Request to create a loan: " + loan);
        if (loan.getLoanId() != null) {
            throw new IllegalArgumentException("Loan ID must be null");
        }
        return loanRepository.save(loan);
    }

    @Override
    public Optional<Loan> getLoanById(Long id) {
        LOG.debug("Request to get loan by id: {}", id);
        return loanRepository.findById(id);
    }
}
