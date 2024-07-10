package com.app.lms.service;

import com.app.lms.dto.*;
import com.app.lms.entity.Instalments;
import com.app.lms.entity.LmsConfig;
import com.app.lms.exception.DefaultException;
import com.app.lms.repository.PaymentRepository;
import com.app.lms.store.InstalmentsStore;
import com.app.lms.store.LmsConfigStore;
import com.app.lms.store.PaymentStore;
import com.app.lms.util.TimeUtil;
import com.app.lms.util.enums.LoanType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InstalmentsService {

    private final InstalmentsStore instalmentsStore;

    private final LmsConfigStore lmsConfigStore;

    private final PaymentStore paymentStore;

    private void createDailyLoanInstallment(CreateLoanRequest body,Long loanId){
        List<Instalments> instalmentsList = new ArrayList<>();
        Integer startSlot = TimeUtil.getNSlot();
        Integer endSlot = body.getEndSlot();
        int noOfInstalments = TimeUtil.getDaysDiff(startSlot,endSlot);
        BigDecimal totalAmount = body.getAmount().multiply(body.getInterest().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        BigDecimal perInstalmentAmount = totalAmount.divide(BigDecimal.valueOf(noOfInstalments),2,RoundingMode.HALF_UP);
        for(int i = 0; i < noOfInstalments; i++){
            Integer nSlot = TimeUtil.getNSlot(i);
            Instalments instalment = Instalments.builder()
                    .loanId(loanId)
                    .amount(perInstalmentAmount)
                    .dueSlot(nSlot)
                    .build();
            instalmentsList.add(instalment);
        }
        instalmentsStore.saveAll(instalmentsList);
    }

    private void createMonthlyLoanInstallment(CreateLoanRequest body,Long loanId){
        Integer startSlot = TimeUtil.getNSlot();
        Integer endSlot = body.getEndSlot();
        Integer dayOfMonth = TimeUtil.getDateByNSlot(startSlot);

        Integer monthlySettlementDay = lmsConfigStore.getMonthlySettlementDate();
        int noOfInstalments = TimeUtil.getMonthsDiff(startSlot,endSlot);
        int deltaDays = monthlySettlementDay - dayOfMonth;
        int upcomingSettlementSlot = TimeUtil.getNSlot(deltaDays);
        if(deltaDays <=0){
            upcomingSettlementSlot = TimeUtil.getNSlot(deltaDays,1);
        }
        BigDecimal totalAmount = body.getAmount().multiply(body.getInterest().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);

        BigDecimal amountPerSettlement = totalAmount.divide(BigDecimal.valueOf(noOfInstalments),2,RoundingMode.HALF_UP);
        BigDecimal remainingAmount = new BigDecimal(totalAmount.toString());

        List<Instalments> instalmentsList = new ArrayList<>();

        if(!dayOfMonth.equals(monthlySettlementDay)){
            Integer daysDiff = TimeUtil.getDaysDiff(startSlot,upcomingSettlementSlot);
            BigDecimal amount = amountPerSettlement.divide(BigDecimal.valueOf(30),0,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(daysDiff));
            Instalments instalment = Instalments.builder()
                    .loanId(loanId)
                    .amount(amount)
                    .dueSlot(upcomingSettlementSlot)
                    .build();
            instalmentsList.add(instalment);
            upcomingSettlementSlot = TimeUtil.addMonthToNSlot(upcomingSettlementSlot,1);
            remainingAmount = remainingAmount.subtract(amount);
        }

        for(int i=0; i < noOfInstalments - 1; i++){
            Instalments instalment = Instalments.builder()
                    .loanId(loanId)
                    .amount(amountPerSettlement)
                    .dueSlot(upcomingSettlementSlot)
                    .build();
            instalmentsList.add(instalment);
            upcomingSettlementSlot = TimeUtil.addMonthToNSlot(upcomingSettlementSlot,1);
            remainingAmount = remainingAmount.subtract(amountPerSettlement);
        }
        if(remainingAmount.compareTo(BigDecimal.valueOf(0))>0){
            Instalments instalment = Instalments.builder()
                    .loanId(loanId)
                    .amount(remainingAmount)
                    .dueSlot(Math.min(upcomingSettlementSlot,body.getEndSlot()))
                    .build();
            instalmentsList.add(instalment);
        }
        instalmentsStore.saveAll(instalmentsList);
    }

    private void createInformalLoanInstallment(CreateLoanRequest body,Long loanId){
        BigDecimal totalAmount = body.getAmount().multiply(body.getInterest().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        Instalments instalment = Instalments.builder()
                .loanId(loanId)
                .amount(totalAmount)
                .dueSlot(body.getEndSlot())
                .build();
        instalmentsStore.save(instalment);
    }

    private void createWeeklyLoan(CreateLoanRequest body,Long loanId){
        Integer startSlot = TimeUtil.getNSlot();
        Integer endSlot = body.getEndSlot();
        Integer dayOfWeek = TimeUtil.getDayOfWeek(startSlot);

        Integer weeklySettlementDay = lmsConfigStore.getWeeklySettlementDay();
        int noOfInstalments = TimeUtil.getWeeksDiff(startSlot,endSlot);
        int deltaDays = weeklySettlementDay - dayOfWeek;
        if(deltaDays<=0){
            deltaDays+=7;
        }
        Integer upcomingSettlementSlot = TimeUtil.getNSlot(deltaDays);
        BigDecimal totalAmount = body.getAmount().multiply(body.getInterest().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        BigDecimal amountPerSettlement = totalAmount.divide(BigDecimal.valueOf(noOfInstalments),2,RoundingMode.HALF_UP);
        BigDecimal remainingAmount = new BigDecimal(totalAmount.toString());
        List<Instalments> instalmentsList = new ArrayList<>();
        if(!dayOfWeek.equals(weeklySettlementDay)){
            Integer daysDiff = TimeUtil.getDaysDiff(startSlot,upcomingSettlementSlot);
            BigDecimal amount = amountPerSettlement.divide(BigDecimal.valueOf(7),0,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(daysDiff));
            Instalments instalment = Instalments.builder()
                    .loanId(loanId)
                    .amount(amount)
                    .dueSlot(upcomingSettlementSlot)
                    .build();
            instalmentsList.add(instalment);
            upcomingSettlementSlot = TimeUtil.addWeekToNSlot(upcomingSettlementSlot,1);
            remainingAmount = remainingAmount.subtract(amount);
        }
        for(int i=0; i < noOfInstalments - 1; i++){
            Instalments instalment = Instalments.builder()
                    .loanId(loanId)
                    .amount(amountPerSettlement)
                    .dueSlot(upcomingSettlementSlot)
                    .build();
            instalmentsList.add(instalment);
            upcomingSettlementSlot = TimeUtil.addWeekToNSlot(upcomingSettlementSlot,1);
            remainingAmount = remainingAmount.subtract(amountPerSettlement);
        }
        if(remainingAmount.compareTo(BigDecimal.valueOf(0))>0){
            Instalments instalment = Instalments.builder()
                    .loanId(loanId)
                    .amount(remainingAmount)
                    .dueSlot(Math.min(upcomingSettlementSlot,body.getEndSlot()))
                    .build();
            instalmentsList.add(instalment);
        }
        instalmentsStore.saveAll(instalmentsList);
    }

    public void createInstalmentsforLoan(CreateLoanRequest body,Long loanId){
        LoanType loanType = body.getLoanType();
        if(loanType == LoanType.DAILY){
            createDailyLoanInstallment(body,loanId);
        } else if (loanType==LoanType.MONTHLY) {
            createMonthlyLoanInstallment(body, loanId);
        } else if (loanType == LoanType.WEEKLY) {
            createWeeklyLoan(body,loanId);
        } else if (loanType == LoanType.INFORMAL) {
            createInformalLoanInstallment(body, loanId);
        }
    }

    public Pagination<InstalmentDto> getPaginatedInstaments(Long loanId, Integer pageNo) {
        Page<InstalmentDto> result =  instalmentsStore.getPaginatedInstalments(pageNo,loanId);
        boolean hasNext = pageNo+1<result.getTotalPages();
        return new Pagination<>(hasNext,result.getContent());
    }


    @Transactional
    public void markAsPaidInstalment(Long instalmentId){
        Instalments instalment = instalmentsStore.getInstalmentById(instalmentId);
        if(instalment.getIsPaid().equals(Boolean.TRUE)){
            throw new DefaultException("Instalment already Paid");
        }
        paymentStore.createPayment(instalment);
        instalment.setIsPaid(true);
        instalmentsStore.save(instalment);
    }

    @Transactional
    public void markAsUnPaidInstalment(Long instalmentId){
        Instalments instalment = instalmentsStore.getInstalmentById(instalmentId);
        if(instalment.getIsPaid().equals(Boolean.FALSE)){
            throw new DefaultException("Instalment Not Paid Yet");
        }
        paymentStore.deletePayment(instalmentId);
        instalment.setIsPaid(false);
        instalmentsStore.save(instalment);
    }

    public Map<Long, BigDecimal> getTotalAmount(List<Long> loanIds) {
        return instalmentsStore.getLoanTotalAmountOfLoanIds(loanIds).stream().collect(Collectors.toMap(
                LoanAmountMapping::getLoanId,
                LoanAmountMapping::getAmount
        ));
    }
}
