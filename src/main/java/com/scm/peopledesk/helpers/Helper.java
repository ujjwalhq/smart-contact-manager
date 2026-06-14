package com.scm.peopledesk.helpers;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

  public static String getEmailOfLoggedInUser(Authentication authentication) {

    
    // if i login with email id password then how to extract
    if (authentication instanceof OAuth2AuthenticationToken) {
      
      
      var aOAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
      var clientId=aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

      var oauth2User=(OAuth2User)authentication.getPrincipal();
      String username="";

      if (clientId.equalsIgnoreCase("google")) {
        
        // sign with google
        System.out.println("Getting email from Google");
        username = (String) oauth2User.getAttribute("email");
        
      }
      else if(clientId.equalsIgnoreCase("github")){
      

      // sign with github
      System.out.println("Getting email from Github");
      username = (String) oauth2User.getAttribute("email") != null
        ? (String) oauth2User.getAttribute("email")
        : (String) oauth2User.getAttribute("login") + "@github.local";
      
      }

      // sign with facebook
      return username;

    }
    else{
      System.out.println("Getting data from local database");
      return authentication.getName();
    }

  }
}
