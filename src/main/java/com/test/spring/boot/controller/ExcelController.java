// PeerReviewController.java
package com.test.spring.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
