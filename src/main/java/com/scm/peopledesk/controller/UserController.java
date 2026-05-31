package com.scm.peopledesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    //user dashboard
    @RequestMapping(value="/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

    //user profile page 
     @RequestMapping(value="/profile")
    public String userProfile(){
        return "user/profile";
    }

    //user add contacts page

    //user view contacts page

    //user edit contacts page 

    //user delete contacts page

    //user search contacts page

}
