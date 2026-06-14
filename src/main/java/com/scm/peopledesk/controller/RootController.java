package com.scm.peopledesk.controller;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.scm.peopledesk.entities.User;
import com.scm.peopledesk.helpers.Helper;
import com.scm.peopledesk.services.UserService;

@ControllerAdvice
public class RootController {

  private Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserService userService;

   @ModelAttribute
    public void addLoggedInUserInformation(Model model,Authentication authentication){
      if (authentication==null) {
        return;
      }

        System.out.print("Adding logged in user information to the model");

        String username=Helper.getEmailOfLoggedInUser(authentication);


        logger.info("User Profile Page - User: {}", username);

        //fetch data from database : get user from db


        User user=(User) userService.getUserByEmail(username);
        System.out.println(user);

        
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedInUser", user);
    }


}
