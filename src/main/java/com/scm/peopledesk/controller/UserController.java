package com.scm.peopledesk.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.peopledesk.helpers.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger=LoggerFactory.getLogger(UserController.class);

    //user dashboard
    @RequestMapping(value="/dashboard")
    public String userDashboard(){
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    //user profile page 
     @RequestMapping(value="/profile")
    public String userProfile(Authentication authentication){
        
        String username=Helper.getEmailOfLoggedInUser(authentication);

        logger.info("User Profile Page - User: {}", username);

        //fetch data from database : get user from db

        System.out.println("User Profile Page - User: " + username);
        return "user/profile";
    }

    //user add contacts page

    //user view contacts page

    //user edit contacts page 

    //user delete contacts page

    //user search contacts page

}
