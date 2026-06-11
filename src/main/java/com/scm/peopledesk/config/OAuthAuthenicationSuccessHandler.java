package com.scm.peopledesk.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.peopledesk.entities.Providers;
import com.scm.peopledesk.entities.User;
import com.scm.peopledesk.helpers.AppConstants;
import com.scm.peopledesk.repsitories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

  Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

  @Autowired
  private UserRepo userRepo;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    System.out.println("HANDLER EXECUTED");

    logger.info("OAuth Authentication Success Handler called");

    // Identify the provider
    var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

    String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
    logger.info("Authorized Client Registration ID: {}", authorizedClientRegistrationId);

    var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
    oauthUser.getAttributes().forEach((key, value) -> {
      logger.info("{} => {}", key, value);
    });

    User user = new User();
    user.setUserId(UUID.randomUUID().toString());
    user.setRolesList(List.of(AppConstants.ROLE_USER));
    user.setEnabled(true);
    user.setEmailVerified(true);

    if (authorizedClientRegistrationId.equals("google")) {
      // google
      // google attributes

      user.setEmail((String) oauthUser.getAttribute("email"));
      user.setProfilePic((String) oauthUser.getAttribute("picture"));
      user.setName((String) oauthUser.getAttribute("name"));
      user.setProviderUserId(oauthUser.getName());
      user.setProvider(Providers.GOOGLE);

    }

    else if (authorizedClientRegistrationId.equals("github")) {
      // github
      // github attributes

      String email = (String) oauthUser.getAttribute("email")!=null ? (String) oauthUser.getAttribute("email") : "email_not_provided_by_github";
      String picture = (String) oauthUser.getAttribute("avatar_url")!=null ? (String) oauthUser.getAttribute("avatar_url") : "picture_not_provided_by_github";
      String name = (String) oauthUser.getAttribute("name")!=null ? (String) oauthUser.getAttribute("name") : "name_not_provided_by_github";  
      String providerUserId = oauthUser.getName() != null ? oauthUser.getName() : "provider_user_id_not_provided_by_github";

      user.setEmail(email);
      user.setProfilePic(picture);
      user.setName(name);
      user.setProviderUserId(providerUserId);
      user.setProvider(Providers.GITHUB);
    }

    else if (authorizedClientRegistrationId.equals("facebook")) {
      // facebook
      // facebook attributes
    }

    else if (authorizedClientRegistrationId.equals("linkedin")) {
      // linkedin
      // linkedin attributes
    }

    else {
      logger.warn("Unknown OAuth provider");
    }

    /*
     * 
     * DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
     * 
     * logger.info(user.getName());
     * 
     * user.getAttributes().forEach((key, value) -> {
     * logger.info("{} => {}", key, value);
     * });
     * 
     * logger.info(user.getAuthorities().toString());
     * 
     * // data save in database
     * 
     * String email = (String) user.getAttribute("email");
     * String name = (String) user.getAttribute("name");
     * String picture = (String) user.getAttribute("picture");
     * 
     * // create user and save in database
     * User user1 = new User();
     * user1.setEmail(email);
     * user1.setName(name);
     * user1.setProfilePic(picture);
     * user1.setPassword("password");
     * user1.setUserId(UUID.randomUUID().toString());
     * user1.setProvider(Providers.GOOGLE);
     * user1.setEnabled(true);
     * user1.setEmailVerified(true);
     * user1.setProviderUserId(user.getName());
     * user1.setRolesList(List.of(AppConstants.ROLE_USER));
     * user1.setAbout("This account is created using google login");
     * 
     * User user2=userRepo.findByEmail(email).orElse(null);
     * if(user2==null){
     * userRepo.save(user1);
     * logger.info("New user created with email: {}", email);
     * }
     * 
     */

    User user2=userRepo.findByEmail((user.getEmail())).orElse(null);
     if(user2==null){
     userRepo.save(user);
     logger.info("New user created with email: {}", user.getEmail());
     }
     

    new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");

  }

}
