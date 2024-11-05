// PeerReviewController.java
package com.test.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.test.spring.boot.readfile.ExcelReaderService;

@RestController
public class ExcelController {

    private final ExcelReaderService excelReaderService;

    @Autowired
    public ExcelController(ExcelReaderService excelReaderService) {
        this.excelReaderService = excelReaderService;
    }

    @PostMapping("/validate-excel")
    public ResponseEntity<String> validateExcel(@RequestParam("file") MultipartFile file) {
        excelReaderService.validateExcelFile(file);
        return ResponseEntity.ok("Validation started");
    }
    
    @GetMapping("/api/username")
    public String getUsername() {
        return System.getProperty("user.name");  // Get the system username
    }
    
    @GetMapping("/details")
    public String showDetailPage(@RequestParam("id") String id) {
        // Pass the ID or any other parameter to the detail page if needed
        return "detailPage"; // name of the HTML file in templates
    }
    
    @GetMapping("/detailPage")
    public String showDetailPage(@RequestParam String story, Model model) {
        model.addAttribute("story", story);
        return "detailPage";
    }
}
