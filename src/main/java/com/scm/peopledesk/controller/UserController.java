package com.scm.peopledesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scm.peopledesk.entities.User;
import com.scm.peopledesk.helpers.Helper;
import com.scm.peopledesk.helpers.Message;
import com.scm.peopledesk.helpers.MessageType;
import com.scm.peopledesk.services.ImageService;
import com.scm.peopledesk.services.UserService;

import java.util.UUID;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

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

    // User profile Edit

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Authentication authentication) {

        User user = userService.getUserByEmail(
                Helper.getEmailOfLoggedInUser(authentication));

        model.addAttribute("user", user);

        return "user/edit-profile";
    }

    // User Profile Update
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute User user,
            @RequestParam("profileImage") MultipartFile profileImage,
            Authentication authentication,
            HttpSession session) {

        // Logged-in user
        User existingUser = userService.getUserByEmail(
                Helper.getEmailOfLoggedInUser(authentication));

        // Update basic information
        existingUser.setName(user.getName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAbout(user.getAbout());

        // Update profile picture
        if (profileImage != null && !profileImage.isEmpty()) {

            // Delete previous Cloudinary image
            if (existingUser.getCloudinaryImagePublicId() != null
                    && !existingUser.getCloudinaryImagePublicId().isBlank()) {

                imageService.deleteImage(
                        existingUser.getCloudinaryImagePublicId());
            }

            // Upload new image
            String fileName = UUID.randomUUID().toString();

            String imageUrl = imageService.uploadImage(
                    profileImage,
                    fileName);

            existingUser.setProfilePic(imageUrl);
            existingUser.setCloudinaryImagePublicId(fileName);
        }

        // Save changes
        userService.updateUser(existingUser);

        // Success message
        session.setAttribute(
                "message",
                Message.builder()
                        .content("Profile updated successfully.")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/profile";
    }

    // user add contacts page

    // user view contacts page

    // user edit contacts page

    // user delete contacts page

    // user search contacts page

}
