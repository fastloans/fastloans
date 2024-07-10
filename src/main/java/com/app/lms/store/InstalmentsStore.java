package com.app.lms.store;

import com.app.lms.dto.InstalmentDto;
import com.app.lms.dto.LoanAmountMapping;
import com.app.lms.entity.Instalments;
import com.app.lms.exception.DefaultException;
import com.app.lms.repository.InstalmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InstalmentsStore {

    private final InstalmentsRepository instalmentsRepository;

    public void saveAll(List<Instalments> instalmentsList) {
        instalmentsRepository.saveAll(instalmentsList);
    }

    public void save(Instalments instalment) {
        instalmentsRepository.save(instalment);
    }

    public Page<InstalmentDto> getPaginatedInstalments(Integer pageNo, Long loanId) {
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.ASC,"id");
        return instalmentsRepository.getPaginatedInstamentsByLoanId(loanId,pageable);
    }

    public Instalments getInstalmentById(Long instalmentId) {
        return instalmentsRepository.findById(instalmentId).orElseThrow(()->new DefaultException(""));
    }

    public List<LoanAmountMapping> getLoanTotalAmountOfLoanIds(List<Long> loanIds) {
        return instalmentsRepository.getLoanTotalAmountMappingList(loanIds);
    }
}
