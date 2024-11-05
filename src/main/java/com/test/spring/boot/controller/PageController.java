package com.test.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHomepage() {
        return "index"; // Assuming you want to load index.html as your homepage
    }

    @GetMapping("/details")
    public String showDetailsPage() {
        return "detailPage"; // Maps to detailPage.html
    }
}
