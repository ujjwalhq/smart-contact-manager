package com.scm.peopledesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.peopledesk.entities.User;
import com.scm.peopledesk.repsitories.UserRepo;


@Controller
@RequestMapping("/auth")
public class AuthController {
      
    // verify email

    @Autowired
    private UserRepo userRepo;
 

    @GetMapping("/verify-email")
   public String verifyEmail(
    @RequestParam("token") 
    String token
   ){
      // System.out.println("Verify Email");

       User user = userRepo.findByEmailToken(token).orElse(null);

       if(user!=null){
        //use fetched keep process

        if(user.getEmailToken().equals(token)){
          user.setEmailVerified(true);
          user.setEnabled(true);
          userRepo.save(user);

          return "success-page";
        }
        return "error-page";
       }

      return "error-page";
   }
    
}
