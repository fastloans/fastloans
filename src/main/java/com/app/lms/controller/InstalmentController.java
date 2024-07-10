package com.app.lms.controller;

import com.app.lms.dto.*;
import com.app.lms.service.InstalmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instalment")
@RequiredArgsConstructor
public class InstalmentController {

    private final InstalmentsService instalmentsService;

    @PostMapping(value = "/list/v{version_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Pagination<InstalmentDto> getInstalmentsPagination(@RequestParam(required = true,value = "p") String page, @RequestBody InstalmentsRequest body, @PathVariable("version_id") Integer versionId){
        Long loanId = body.getLoanId();
        Integer pageNo = Integer.parseInt(page);
        return instalmentsService.getPaginatedInstaments(loanId,pageNo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/mark/as/paid/{id}/v{version_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Message> markPaidInstalment(@PathVariable("id") Long instalmentId, @PathVariable("version_id") Integer versionId){
        instalmentsService.markAsPaidInstalment(instalmentId);
        Message message = new Message("Instalment Paid Successfully!");
        return new Response<>(message);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/mark/as/unpaid/{id}/v{version_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Message> markUnPaidInstalment(@PathVariable("id") Long instalmentId,@PathVariable("version_id") Integer versionId){
        instalmentsService.markAsUnPaidInstalment(instalmentId);
        Message message = new Message("Instalment Marked as Unpaid Successfully!");
        return new Response<>(message);
    }
}
