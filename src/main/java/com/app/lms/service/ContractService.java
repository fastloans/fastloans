package com.app.lms.service;

import com.app.lms.dto.ContractDto;
import com.app.lms.dto.ContractResponseDto;
import com.app.lms.entity.Contract;
import com.app.lms.store.ContractStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ContractService {

    private final ContractStore contractStore;


    private static final Map<String, String> MIME_TYPES;

    static {
        MIME_TYPES = new HashMap<>();
        MIME_TYPES.put("jpg", "image/jpeg");
        MIME_TYPES.put("jpeg", "image/jpeg");
        MIME_TYPES.put("png", "image/png");
    }

    public void saveContractForLoan(Long loanId, List<MultipartFile> images){
        for(MultipartFile image :images){
            if(!image.isEmpty()){
                String fileName = image.getOriginalFilename();
                byte[] fileData;
                try {
                    fileData = image.getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to read file: " + e.getMessage());
                }
                Contract contract = Contract.builder()
                        .loanId(loanId)
                        .fileName(fileName)
                        .imageContent(fileData)
                        .build();
                contractStore.save(contract);
            }
        }
    }

    public ContractResponseDto getContractImage(Long contractId) {
        Contract contract = contractStore.getContract(contractId);
        String fileExt = List.of(contract.getFileName().split("\\.")).getLast();
        String contentType = MIME_TYPES.get(fileExt);
        return new ContractResponseDto(contract.getImageContent(),contentType);
    }

    public List<ContractDto> getContractImageIds(Long loanId) {
        return contractStore.getContractImageIds(loanId);
    }
}
