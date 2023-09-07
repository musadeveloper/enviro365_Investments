package com.enviro.assessment.grad001.musawenkosimahlangu.controller;


import com.enviro.assessment.grad001.musawenkosimahlangu.model.Investor;
import com.enviro.assessment.grad001.musawenkosimahlangu.model.WithdrawalNotice;
import com.enviro.assessment.grad001.musawenkosimahlangu.service.CsvFileExportService;
import com.enviro.assessment.grad001.musawenkosimahlangu.service.InvestorService;
import com.enviro.assessment.grad001.musawenkosimahlangu.service.WithdrawalNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class EnviroController {

    private final InvestorService investorService;
    private final WithdrawalNoticeService withdrawalNoticeService;
    private final CsvFileExportService csvFileExportService;


    @Autowired
    public EnviroController(InvestorService investorService, WithdrawalNoticeService withdrawalNoticeService, CsvFileExportService csvFileExportService) {
        this.investorService = investorService;
        this.withdrawalNoticeService = withdrawalNoticeService;
        this.csvFileExportService = csvFileExportService;
    }


    @GetMapping("/investor/{id}")
    public ResponseEntity<Investor> getInvestorById(@PathVariable Long id) {
        Investor investor = investorService.getInvestorById(id);
        if (investor != null) {
            return ResponseEntity.ok(investor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawFromProduct(@RequestParam Long productId,@RequestParam double withdrawalAmount) {
        try {
            withdrawalNoticeService.createWithdrawalNotice(productId, withdrawalAmount);
            return ResponseEntity.ok("Withdrawal notice successful.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Withdrawal failed: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportNoticesToCsv(
            @RequestParam Long productId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate startDateParsed = LocalDate.parse(startDate);
            LocalDate endDateParsed = LocalDate.parse(endDate);

            List<WithdrawalNotice> notices = withdrawalNoticeService.getNoticesByProductAndDateRange(productId, startDateParsed, endDateParsed);

            if (notices.isEmpty()) {
                return ResponseEntity.badRequest().body("No withdrawal notices found for the given criteria.");
            }

            String filePath = "notices.csv";

            csvFileExportService.exportNoticesToCsv(notices, filePath);

            return ResponseEntity.ok("CSV file exported successfully.");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Please provide dates in the format 'yyyy-MM-dd'.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to export CSV: " + e.getMessage());
        }
    }

}
