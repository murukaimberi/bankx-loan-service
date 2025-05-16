/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/16
 * Time   : 08:26
 */

package za.co.afrikatek.bankxloanservice.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private static final Logger LOG = LoggerFactory.getLogger(LoanController.class);
    private final LoanService loanService;
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     *
     * @param loan the loan request body to create the loan
     * @return {@code 201} when
     * @throws URISyntaxException
     */
    @PostMapping("")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) throws URISyntaxException {
        LOG.info("Rest request to create a loan: {}", loan);
        var savedLoan = loanService.createLoan(loan);
        return ResponseEntity.created(new URI("/loans/" + savedLoan.getLoanId())).body(savedLoan);
    }

    /**
     *
     * @param loanId the id of the loan to be retrieved
     * @return
     */
    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long loanId) {
        LOG.info("Rest request to get an loan: {}", loanId);
        Optional<Loan> loan = loanService.getLoanById(loanId);
        return loan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
