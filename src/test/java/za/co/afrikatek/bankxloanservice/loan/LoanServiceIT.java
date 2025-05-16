/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/16
 * Time   : 08:35
 */

package za.co.afrikatek.bankxloanservice.loan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class LoanServiceIT {
    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void whenCreateLoan_thenSuccess() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(500000));
        loan.setTerm(36);
    }
}
