/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 16:14
 */

package za.co.afrikatek.bankxloanservice.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class LoanControllerTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
    }

    @Test
    void whenCreateLoan_ThenSuccess()throws Exception {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(540000.45));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        mockMvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(loan)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void whenCreateLoan_And_LoanAmountIsNegative_ThenThrowException()throws Exception {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(-540000.45));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        mockMvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(loan)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void whenCreateLoan_And_TermIsNegative_ThenThrowException()throws Exception {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(540000.45));
        loan.setTerm(-36);
        loan.setStatus(LoanStatus.ACTIVE);
        mockMvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(loan)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void whenCreateLoan_And_LoanIdIsGiven_ThenThrowException()throws Exception {
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setLoanAmount(BigDecimal.valueOf(540000.45));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        mockMvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(loan)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void whenFindById_Then_Succeed() throws Exception {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(540000.45));
        loan.setTerm(36);
        loan.setStatus(LoanStatus.ACTIVE);
        loan = loanService.createLoan(loan);

        Loan foundLoan = objectMapper.readValue(mockMvc.perform(get("/loans/{loanId}", loan.getLoanId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(), Loan.class);
        Assertions.assertEquals(loan.getLoanId(), foundLoan.getLoanId());
        Assertions.assertEquals(loan.getLoanAmount(), foundLoan.getLoanAmount());
        Assertions.assertEquals(loan.getTerm(), foundLoan.getTerm());
        Assertions.assertEquals(loan.getStatus(), foundLoan.getStatus());
    }

    @Test
    void whenFindById_And_LoanIdIsNotFound_ThenThrowException() throws Exception {
        mockMvc.perform(get("/loans/{loanId}", 1L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
