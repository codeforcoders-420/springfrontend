// PeerReviewController.java
package com.test.spring.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.test.spring.boot.readfile.ExcelReaderService;

@RestController
public class ExcelController {

    private final ExcelReaderService excelReaderService;

    public ExcelController(ExcelReaderService excelReaderService) {
        this.excelReaderService = excelReaderService;
        System.out.println("ExcelController initialized!");
    }

    // Any other REST endpoints or functionalities can remain here

}