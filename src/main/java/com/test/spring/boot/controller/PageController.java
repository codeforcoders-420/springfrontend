package com.test.spring.boot.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
	
	private static final String SHARE_FOLDER_PATH = "C:\\Users\\rajas\\Desktop\\TFS Search\\";

    @GetMapping("/")
    public String showHomepage() {
        return "index"; // Assuming you want to load index.html as your homepage
    }

    @GetMapping("/details")
    public String showDetailsPage() {
        return "detailPage"; // Maps to detailPage.html
    }
    
    @GetMapping("/openRGStory")
    public String openRGStory(@RequestParam("storyNumber") String storyNumber, Model model) {
        // Create the folder if it does not exist
        File folder = new File(SHARE_FOLDER_PATH, storyNumber);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                model.addAttribute("errorMessage", "Failed to create folder for story number: " + storyNumber);
                return "errorPage"; // Redirect to an error page if folder creation fails
            }
        }

        // Add the story number to the model
        model.addAttribute("storyNumber", storyNumber);

        // Return the name of the template
        return "detailPage";
    }
}
