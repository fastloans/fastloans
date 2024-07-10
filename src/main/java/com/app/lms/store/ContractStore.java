package com.app.lms.store;

import com.app.lms.dto.ContractDto;
import com.app.lms.entity.Contract;
import com.app.lms.exception.DefaultException;
import com.app.lms.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContractStore {

    private final ContractRepository contractRepository;

    public void save(Contract contract){
        contractRepository.save(contract);
    }

    public List<ContractDto> getContractImageIds(Long loanId) {
        return contractRepository.getContractIdByLoan(loanId);
    }

    public Contract getContract(Long contractId) {
        return contractRepository.findById(contractId).orElseThrow(()->new DefaultException("No Image Found"));
    }
}
