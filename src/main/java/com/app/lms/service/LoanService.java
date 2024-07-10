package com.app.lms.service;

import com.app.lms.dto.*;
import com.app.lms.entity.Loan;
import com.app.lms.store.LmsConfigStore;
import com.app.lms.store.LoanStore;
import com.app.lms.store.PaymentStore;
import com.app.lms.util.TimeUtil;
import com.app.lms.util.enums.LoanType;
import com.app.lms.util.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanStore loanStore;

    private final InstalmentsService instalmentsService;

    private final ContractService contractService;

    private final PaymentStore paymentStore;

    private final LmsConfigStore lmsConfigStore;

    @Transactional
    public void createLoan(CreateLoanRequest body, Long userId, List<MultipartFile> images) {

        Loan loan = Loan.builder()
                .loanType(body.getLoanType())
                .userId(userId)
                .amount(body.getAmount())
                .interest(body.getInterest())
                .startSlot(TimeUtil.getNSlot())
                .endSlot(body.getEndSlot())
                .remark(body.getRemark())
                .interest(body.getInterest())
                .build();
        loan = loanStore.save(loan);

        Long loanId = loan.getId();
        instalmentsService.createInstalmentsforLoan(body, loanId);
        if(!images.isEmpty()){
            contractService.saveContractForLoan(loanId,images);
        }
    }

    public Pagination<LoanPage> getPaginatedLoans(Long userId, Integer pageNo) {
        Page<Loan> result =  loanStore.getUserLoanPaginated(userId,pageNo);
        boolean hasNext = (pageNo+1)<result.getTotalPages();
        List<Long> loanIds = result.getContent().stream().map(Loan::getId).toList();
        Map<Long,BigDecimal> loanTotalAmountMapping = instalmentsService.getTotalAmount(loanIds);
        Map<Long, BigDecimal> loanPaidAmountMapping =  paymentStore.getPaidAmountByLoanIds(loanIds);
        List<LoanPage> page = result.getContent().stream().map(i -> {
            Long id = i.getId();
            BigDecimal totalAmount = loanTotalAmountMapping.get(id);
            BigDecimal paidAmount = loanPaidAmountMapping.getOrDefault(id,BigDecimal.ZERO);
            BigDecimal percent = paidAmount.divide(totalAmount, RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100));
            return LoanPage.builder()
                    .id(i.getId())
                    .amount(i.getAmount())
                    .interest(i.getInterest())
                    .loanType(i.getLoanType())
                    .totalAmount(totalAmount)
                    .paidAmount(paidAmount)
                    .percentPaid(percent)
                    .build();
        }
        ).toList();
        return new Pagination<>(hasNext,page);
    }

    public LoanDto getLoanById(Long loanId) {
        return loanStore.getLoanById(loanId);
    }



    public DashboardAmountDto getDashboardAmount() {
        BigDecimal totalMoneyInPool = lmsConfigStore.getTotalMoneyInPool();  //1lakh
        BigDecimal totalPayment = paymentStore.getTotalPaymentAmount();      //10k payment instalment

        BigDecimal balance = BigDecimal.TEN;
        BigDecimal dueBalance = BigDecimal.ONE;
        return new DashboardAmountDto(balance,dueBalance);
    }
}