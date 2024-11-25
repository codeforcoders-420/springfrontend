// ReadDataFromExcel.java
package com.test.spring.boot.readfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.test.spring.boot.model.ValidationResult;

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
			
			sendProgressUpdate(10, "Validation complete! Report saved.");
			
			sendProgressUpdate(30, "Validation complete! Report saved.");
			
			sendProgressUpdate(50, "Validation complete! Report saved.");

			sendProgressUpdate(100, "Validation complete! Report saved.");

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
	
	 public ValidationResult startValidation() {
	        ValidationResult result = new ValidationResult();

	        try {
	            // Perform the validation logic
	            int progress = 100; // Simulate progress completion
	            String message = "Validation completed successfully.";
	            String outputFolderLocation = "C:\\Users\\rajas\\Desktop\\TFS Search\\123456";

	            // Set the validation results
	            result.setProgressPercentage(progress);
	            result.setMessage(message);
	            result.setOutputFolderLocation(outputFolderLocation);

	        } catch (Exception e) {
	            // Handle exceptions
	            result.setProgressPercentage(0);
	            result.setMessage("Validation failed: " + e.getMessage());
	            result.setOutputFolderLocation(null);
	        }

	        return result;
	    }
}
