package com.scm.peopledesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {
   @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name","Ujjwal");
        model.addAttribute("email","ujjwalhq@gmail.com");
        System.out.println("Home Page");
        return "home"; 
    }

    //About Route
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    //Service Route
     @GetMapping("/services")
    public String services(){
        return "services";
    }
}
