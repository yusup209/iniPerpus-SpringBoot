package com.kkp.iniperpus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    /**
     * Serve the reports page
     */
    @GetMapping("/reports")
    public String reportsPage() {
        return "reports";
    }
}
