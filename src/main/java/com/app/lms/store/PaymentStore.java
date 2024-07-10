package com.app.lms.store;

import com.app.lms.dto.LoanAmountMapping;
import com.app.lms.entity.Instalments;
import com.app.lms.entity.Payment;
import com.app.lms.exception.DefaultException;
import com.app.lms.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PaymentStore {

    private final PaymentRepository paymentRepository;

    public void createPayment(Instalments instalment){
        Payment payment = Payment.builder()
                .instalmentId(instalment.getId())
                .loanId(instalment.getLoanId())
                .amount(instalment.getAmount().add(instalment.getFine()))
                .build();
        paymentRepository.save(payment);
    }

    public void deletePayment(Long instamentId){
        Payment payment = paymentRepository.findByInstalmentId(instamentId).orElseThrow(()->new DefaultException("No payment Found"));
        paymentRepository.delete(payment);

    }

    public Map<Long, BigDecimal> getPaidAmountByLoanIds(List<Long> loanIds) {
        return paymentRepository.getPaidAmountByLoanId(loanIds).stream().collect(Collectors.toMap(
                LoanAmountMapping::getLoanId,
                LoanAmountMapping::getAmount
        ));
    }

    public BigDecimal getTotalPaymentAmount() {
        return paymentRepository.getTotalPayment();
    }
}
