// ReadDataFromExcel.java
package com.test.spring.boot.readfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
public class ExcelReaderService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ExcelReaderService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void validateExcelFile(MultipartFile file) {
        try {
            int totalSteps = 100;
            int currentStep = 0;

           
            // Report generation logic (simplified)
            String reportPath = "C:/Users/Reports/ValidationReport.xlsx";  // Sample path
            sendProgressUpdate(100, "Validation complete! Report saved.");
            sendProgressUpdate(100, "link:" + reportPath);  // Send report path

        } catch (Exception e) {
            sendProgressUpdate(0, "Error during validation: " + e.getMessage());
        }
    }

    private void sendProgressUpdate(int percentage, String message) {
        messagingTemplate.convertAndSend("/topic/progress", "progress:" + percentage);
        messagingTemplate.convertAndSend("/topic/progress", "message:" + message);
    }

	public String simulateProgressTask(int progress) {
		// TODO Auto-generated method stub
		return null;
	}
}

