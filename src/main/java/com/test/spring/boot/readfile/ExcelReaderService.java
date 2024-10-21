// ReadDataFromExcel.java
package com.test.spring.boot.readfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
public class ExcelReaderService {

    // Simulate progress task and return messages at each step
    public String simulateProgressTask(int progress) {
        String message = "Task is displayed for the message for the longer messages accomdate place holder " + progress + "% completed.";
        System.out.println(message);  // Log to console
        return message;  // Return the message to be sent to the front end
    }
}