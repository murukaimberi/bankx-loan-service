/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/16
 * Time   : 08:17
 */

package za.co.afrikatek.bankxloanservice.loan;

import org.springframework.data.jpa.repository.JpaRepository;

interface LoanRepository extends JpaRepository<Loan, Long> {
}
