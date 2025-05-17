/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/16
 * Time   : 08:19
 */

package za.co.afrikatek.bankxloanservice.loan;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import za.co.afrikatek.bankxloanservice.exception.Status400BadRequestException;
import za.co.afrikatek.bankxloanservice.payment.PaymentRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(LoanServiceImpl.class);

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan createLoan(Loan loan) {
        LOG.debug("Request to create a loan: " + loan);
        if (loan.getLoanId() != null) {
            throw new Status400BadRequestException("Loan ID must be null", "Loan", Map.of("id", List.of("Loan ID must be null")));
        }
        return loanRepository.save(loan);
    }

    @Override
    public Optional<Loan> getLoanById(Long id) {
        LOG.debug("Request to get loan by id: {}", id);
        return loanRepository.findById(id);
    }

    @Override
    public Loan updateLoan(BigDecimal paymentAmount, Long loanId) {
        LOG.debug("Request to update loan by id: {} by paying amount : {}", loanId, paymentAmount);
        Loan loan = getLoanById(loanId).orElseThrow(() -> new Status400BadRequestException("Loan not found", "Loan", Map.of("loanId", List.of("Loan not found"))));
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new Status400BadRequestException("Loan settled.", "Loan", Map.of("status", List.of("Loan has been settled.")));
        }
        BigDecimal loanAmount = loan.getLoanAmount();
        if (loanAmount.compareTo(paymentAmount) >= 0) {
            loan.setLoanAmount(loanAmount.subtract(paymentAmount));
            return loanRepository.save(loan);
        }
        loan.setLoanAmount(BigDecimal.ZERO);
        loan.setStatus(LoanStatus.SETTLED);
        return loanRepository.save(loan);
    }
}
