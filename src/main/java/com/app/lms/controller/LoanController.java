package com.app.lms.controller;

import com.app.lms.dto.*;
import com.app.lms.framework.Session;
import com.app.lms.service.InstalmentsService;
import com.app.lms.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    private final InstalmentsService instalmentsService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/v{version_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Message> createLoan(@RequestPart("body") CreateLoanRequest body, @RequestPart("images") List<MultipartFile> images, @PathVariable("version_id") Integer versionId){
        AuthenticatedUser user = Session.getAuthenticatedUser();
        Long userId = user.getUserId();
        loanService.createLoan(body,userId,images);
        Message message = new Message("Provided Loan Successfully.");
        return new Response<>(message);
    }

    @GetMapping(value = "/v{version_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Pagination<LoanPage> getLoansPagination(@RequestParam(required = true,value = "p") String page,@PathVariable("version_id") Integer versionId){
        AuthenticatedUser user = Session.getAuthenticatedUser();
        Long userId = user.getUserId();
        Integer pageNo = Integer.parseInt(page);
        return loanService.getPaginatedLoans(userId,pageNo);
    }

    @GetMapping (value = "/{id}/v{version_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<LoanDto> getLoanByLoanId(@PathVariable("id") Long loanId,@PathVariable("version_id") Integer versionId){
        LoanDto response = loanService.getLoanById(loanId);
        return new Response<>(response);
    }
}
