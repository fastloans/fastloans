package com.app.lms.repository;

import com.app.lms.dto.InstalmentDto;
import com.app.lms.dto.LoanAmountMapping;
import com.app.lms.entity.Instalments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstalmentsRepository extends JpaRepository<Instalments, Long> {

    @Query("SELECT new com.app.lms.dto.InstalmentDto(i.id,i.amount,i.dueSlot,i.fine,i.isPaid) from Instalments i WHERE i.loanId = :loanId")
    Page<InstalmentDto> getPaginatedInstamentsByLoanId(@Param("loanId") Long loanId, Pageable pageable);

    @Query("SELECT new com.app.lms.dto.LoanAmountMapping(i.loanId,SUM(i.amount),SUM(i.fine)) from Instalments i GROUP BY i.loanId")
    List<LoanAmountMapping> getLoanTotalAmountMappingList(List<Long> loanIds);
}