package com.app.lms.controller;

import com.app.lms.dto.ContractDto;
import com.app.lms.dto.ContractResponseDto;
import com.app.lms.dto.Response;
import com.app.lms.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping(value = "/loan/{loan_id}/v{version_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ContractDto>> getContractImages(@PathVariable("loan_id") Long loanId,@PathVariable("version_id") Integer versionId){
        List<ContractDto> response = contractService.getContractImageIds(loanId);
        return new Response<>(response);
    }

    @GetMapping(value = "/{id}/v{version_id}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getContractImage(@PathVariable("id") Long contractId,@PathVariable("version_id") Integer versionId){
        ContractResponseDto response = contractService.getContractImage(contractId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", response.getContentType());
        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }
}
