package com.app.lms.controller;

import com.app.lms.dto.DashboardAmountDto;
import com.app.lms.dto.Response;
import com.app.lms.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final LoanService loanService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/balance/details/v{version_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<DashboardAmountDto> getAmountNumbers(@PathVariable("version_id") Integer versionId){
        DashboardAmountDto response = loanService.getDashboardAmount();
        return new Response<>(response);
    }
}
