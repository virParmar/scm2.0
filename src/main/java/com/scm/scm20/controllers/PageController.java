package com.scm.scm20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.helpers.Message;
import com.scm.scm20.helpers.MessageType;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        // System.out.println("Home page controller.");

        // sending data to view
        model.addAttribute("name", "Virendra Parmar");
        model.addAttribute("youtubeChannel", "Vir Parmar");
        model.addAttribute("about", "I am Virendra Parmar");
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/services")
    public String services() {
        return "services";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    // registration page
    @RequestMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        // Can add default data as well
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // this is showing login page
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {

        // validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/man-user-circle-icon.png")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/man-user-circle-icon.png");

        User savedUser = userService.saveUser(user);
        System.out.println(savedUser);

        // set message
        Message message = Message.builder()
                .content("Registration Successfull")
                .type(MessageType.green)
                .build();
        session.setAttribute("message", message);

        return "redirect:/register";
    }
}
