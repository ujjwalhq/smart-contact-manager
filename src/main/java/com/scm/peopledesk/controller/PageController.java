package com.scm.peopledesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.peopledesk.forms.UserForm;



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

     //Service Route
     @GetMapping("/contacts")
    public String contacts(){
        return "contacts";
    }

     //Service Route
     @GetMapping("/login")
    public String login(){
        return "login";
    }

     //Service Route
     @GetMapping("/signup")
    public String signup(Model model){

        UserForm userForm=new UserForm();
        // userForm.setName("Ujjwal");
        model.addAttribute("userForm", userForm);

        return "signup";
    }

    //processing signup
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processSignup(@ModelAttribute UserForm userForm) {
        System.out.println("Processing resgistration");
        //fetch form data
        //UserForm
        System.out.println(userForm);

        //validate form data
        
        //save to database

        //userservice

        //message = "Registration Successful"
        //redirect to login page

        return "redirect:/signup";
    }
    
    
}
