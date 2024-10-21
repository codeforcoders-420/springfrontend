// ReadDataFromExcel.java
package com.test.spring.boot.readfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
public class ExcelReaderService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;  // Spring Boot's WebSocket messaging tool

    public void validateExcelFile(MultipartFile file) {
        try {
            // Initialize variables for progress calculation
            int totalSteps = 100;  // Assume 100 steps for demo (adjust based on actual logic)
            int currentStep = 0;

            // Read and process Excel file (just a sample, customize with actual logic)
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                // Process each row, connect to MySQL, validate data, etc.
                // For example, extract values from Excel cells and perform MySQL operations
                String cellValue = row.getCell(0).getStringCellValue();  // Get data from cell
                System.out.println("Processing row with value: " + cellValue);
                
                // Call MySQL validation logic here
                // Update progress percentage
                currentStep += 5;  // Increment step
                int progressPercentage = (currentStep * 100) / totalSteps;

                // Send progress percentage and message to WebSocket
                String message = "Processed row: " + cellValue;
                sendProgressUpdate(progressPercentage, message);

                // Simulate some delay for demonstration purposes
                Thread.sleep(500);  // Remove this in real implementation
            }

            // Send completion message
            sendProgressUpdate(100, "Validation complete!");
        } catch (Exception e) {
            // Handle exceptions and send error messages to the frontend
            sendProgressUpdate(0, "Error during validation: " + e.getMessage());
        }
    }

    // Helper method to send progress and message to frontend
    private void sendProgressUpdate(int percentage, String message) {
        messagingTemplate.convertAndSend("/topic/progress", "progress:" + percentage);
        messagingTemplate.convertAndSend("/topic/progress", "message:" + message);
    }
}
