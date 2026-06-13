package com.scm.peopledesk.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

  public static String getEmailOfLoggedInUser(Authentication authentication) {

    
    // if i login with email id password then how to extract
    if (authentication instanceof OAuth2AuthenticationToken) {
      
      
      var aOAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
      var clientId=aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

      var oauth2User=(OAuth2User)authentication.getPrincipal();
      String usernane="";

      if (clientId.equalsIgnoreCase("google")) {
        
        // sign with google
        System.out.println("Getting email from Google");
        usernane = (String) oauth2User.getAttribute("email");
        
      }
      else if(clientId.equalsIgnoreCase("github")){
      

      // sign with github
      System.out.println("Getting email from Github");
      usernane = (String) oauth2User.getAttribute("email")!=null ? (String) oauth2User.getAttribute("email") : "email_not_provided_by_github";
      
      }

      // sign with facebook
      return usernane;

    }
    else{
      System.out.println("Getting data from local database");
      return authentication.getName();
    }

  }
}
