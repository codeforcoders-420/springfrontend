// PeerReviewController.java
package com.test.spring.boot.controller;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

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
import com.test.spring.boot.readfile.SourceFileValidation;

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

}
