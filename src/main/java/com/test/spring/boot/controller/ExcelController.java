// PeerReviewController.java
package com.test.spring.boot.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.test.spring.boot.model.ValidationResult;
import com.test.spring.boot.readfile.ExcelReaderService;
import com.test.spring.boot.readfile.SourceFileValidation;
import com.test.spring.boot.readfile.ClaimsImpactAnalysis;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
public class ExcelController {

	@Autowired
	private ExcelReaderService excelReaderService;

	@Autowired
	private SourceFileValidation sourceFileValidation;

	private static final String SHARE_FOLDER_PATH = "C:\\Users\\rajas\\Desktop\\TFS Search\\";

	@PostMapping("/validate-excel")
	public ResponseEntity<String> validateExcel(@RequestParam("file") MultipartFile file) {
		excelReaderService.validateExcelFile(file);
		return ResponseEntity.ok("Validation started");
	}

	@PostMapping("/validate-files")
	public ResponseEntity<String> validateFiles(@RequestParam("lastSourceFile") MultipartFile lastSourceFile,
			@RequestParam("currentSourceFile") MultipartFile currentSourceFile) {
		sourceFileValidation.validatesourceFile(lastSourceFile);
		return ResponseEntity.ok("Validation started");
	}

	@GetMapping("/api/username")
	public String getUsername() {
		return System.getProperty("user.name"); // Get the system username
	}

	@GetMapping("/checkFolder")
	public ResponseEntity<Map<String, Object>> checkFolder(@RequestParam("folderName") String folderName) {
		if (folderName == null || folderName.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Folder name cannot be empty."));
		}
		File folder = new File(SHARE_FOLDER_PATH + folderName);
		return ResponseEntity.ok(Map.of("exists", folder.exists()));
	}

	@PostMapping("/createFolder")
	public ResponseEntity<Map<String, Object>> createFolder(@RequestParam String folderName) {
		File folder = new File(SHARE_FOLDER_PATH + folderName);
		Map<String, Object> response = new HashMap<>();
		try {
			if (!folder.exists()) {
				folder.mkdir();
				response.put("success", true);
			} else {
				response.put("success", false);
				response.put("message", "Folder already exists.");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error creating folder: " + e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/start-peer-review-validation")
	public ResponseEntity<Map<String, Object>> startPeerReviewValidation() {
		Map<String, Object> response = new HashMap<>();

		try {
			// Start the validation process
			ValidationResult result = excelReaderService.startValidation();

			// Prepare the response
			response.put("progress", result.getProgressPercentage());
			response.put("message", result.getMessage());
			response.put("outputLocation", result.getOutputFolderLocation());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("progress", 0);
			response.put("message", "Validation failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/get-output-file-path")
	public String getOutputFilePath() {
		return excelReaderService.getOutputFilePath();
	}
	
	@GetMapping("/preview")
	public List<List<String>> previewExcel() throws IOException {
        String filePath = "C:\\Users\\rajas\\Desktop\\Excelcompare\\PreviewTemplate.xlsx"; // Replace with your shared path
        List<List<String>> excelData = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                excelData.add(rowData);
            }
        }
        return excelData; // Returns the Excel data as a JSON object
    }
	
	@PostMapping("/process-impact-report")
	public ResponseEntity<String> processImpactReport(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("deployedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deployedDate) {
	    try {
	        // Save file to a temporary location or process directly
	        Path tempFile = Files.createTempFile("impact-report-", file.getOriginalFilename());
	        file.transferTo(tempFile.toFile());

	        // Call the ClaimsImpactAnalysis method with file location and date
	        ClaimsImpactAnalysis analysis = new ClaimsImpactAnalysis();
	        analysis.process(tempFile.toString(), deployedDate.toString());

	        // Clean up temp file
	        Files.deleteIfExists(tempFile);

	        return ResponseEntity.ok("Impact report processed successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}

}
