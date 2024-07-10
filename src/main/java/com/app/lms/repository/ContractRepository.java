package com.app.lms.repository;

import com.app.lms.dto.ContractDto;
import com.app.lms.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT new com.app.lms.dto.ContractDto(c.id)  FROM Contract c WHERE c.loanId = :loanId")
    List<ContractDto> getContractIdByLoan(@Param("loanId") Long loanId);
}