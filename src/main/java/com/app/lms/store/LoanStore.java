package com.app.lms.store;

import com.app.lms.dto.LoanDto;
import com.app.lms.entity.Loan;
import com.app.lms.exception.DefaultException;
import com.app.lms.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanStore {

    private final LoanRepository loanRepository;

    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    public Page<Loan> getUserLoanPaginated(Long userId, Integer pageNo) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,"id");
        return loanRepository.getPaginatedLoanOfUser(userId,pageable);
    }

    public LoanDto getLoanById(Long loanId) {
        return loanRepository.getLoanDtoById(loanId).orElseThrow(()->new DefaultException("No Loan Found"));
    }
}
