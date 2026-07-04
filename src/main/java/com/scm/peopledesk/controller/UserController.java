package com.scm.peopledesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.peopledesk.entities.User;
import com.scm.peopledesk.helpers.Helper;
import com.scm.peopledesk.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // user dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "user/profile";
    }

    // Export Contacts Page
    @RequestMapping("/export")
    public String exportPage(Model model, Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        model.addAttribute("loggedInUser", user);

        return "user/export";
    }

    // user add contacts page

    // user view contacts page

    // user edit contacts page

    // user delete contacts page

    // user search contacts page

}
