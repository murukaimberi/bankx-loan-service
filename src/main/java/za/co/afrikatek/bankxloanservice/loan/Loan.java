/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/16
 * Time   : 08:15
 */

package za.co.afrikatek.bankxloanservice.loan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "loans")
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;
    @NotNull
    @Column(precision = 21, scale = 2, nullable = false)
    private BigDecimal loanAmount;
    @NotNull
    @Column(nullable = false)
    private Integer term;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(loanId, loan.loanId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(loanId);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", loanAmount=" + loanAmount +
                ", term=" + term +
                ", status=" + status +
                '}';
    }
}
